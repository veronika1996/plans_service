package com.plans_service.service;

import com.plans_service.dto.PlanDTO;
import com.plans_service.dto.PlanRecipeDTO;
import com.plans_service.entity.PlanEntity;
import com.plans_service.entity.PlanRecipeEntity;
import com.plans_service.entity.RecipeClient;
import com.plans_service.repository.PlanRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class PlanService {

  private static final String PLAN_DOES_NOT_EXIST = "Plan nije pronadjen za datum: ";

  private final PlanRepository planRepository;
  private final RecipeClient recipeClient;

  @Transactional
  public PlanDTO createOrUpdatePlan(@Valid PlanDTO planDTO) {
    Optional<PlanEntity> existingEntity =
        planRepository.findByDateAndUsername(planDTO.getDate(), planDTO.getUsername());
    existingEntity.ifPresent(planEntity -> planDTO.setId(planEntity.getId()));
    PlanEntity planEntity = this.mapToEntity(planDTO);
    PlanEntity savedEntity = planRepository.save(planEntity);
    return mapToDto(savedEntity);
  }

  public PlanDTO getPlanByDate(String date, String username) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(date, formatter);
    PlanEntity entity =
        planRepository
            .findByDateAndUsername(localDate, username)
            .orElseThrow(() -> new IllegalArgumentException(PLAN_DOES_NOT_EXIST + date));
    return mapToDto(entity);
  }

  @Transactional
  public PlanDTO mapToDto(PlanEntity entity) {
    List<PlanRecipeDTO> planRecipes = new ArrayList<>();
    List<PlanRecipeEntity> filteredRecipes = new ArrayList<>();

    for (PlanRecipeEntity recipe : entity.getPlanRecipes()) {
      Integer calories = recipeClient.getRecipesCalories(recipe.getRecipeId());
      if (calories != null) {
        planRecipes.add(new PlanRecipeDTO(recipe.getRecipeId(), calories, recipe.getRecipeCategory()));
        filteredRecipes.add(recipe);
      }
    }

    if (!filteredRecipes.isEmpty()) {
      entity.setPlanRecipes(new ArrayList<>(filteredRecipes));
      planRepository.save(entity);
    }

    return new PlanDTO(
        entity.getId(),
        planRecipes,
        entity.getUsername(),
        entity.getDate(),
        entity.getConsumedCalories()
    );

  }


  public PlanEntity mapToEntity(PlanDTO dto) {
    PlanEntity entity = new PlanEntity();
    if (dto.getId() != null) {
      entity.setId(dto.getId());
    }
    entity.setUsername(dto.getUsername());
    entity.setDate(dto.getDate());
    List<PlanRecipeEntity> planRecipeEntities =
        dto.getPlanRecipes().stream()
            .map(rd -> new PlanRecipeEntity(entity, rd.getRecipeId(), rd.getRecipeCategory()))
            .toList();

    entity.setPlanRecipes(planRecipeEntities);
    entity.setConsumedCalories(
        recipeClient.calculateConsumedCalories(
            dto.getPlanRecipes().stream().map(PlanRecipeDTO::getRecipeId).toList()));

    return entity;
  }

  public PlanService(PlanRepository planRepository, RecipeClient recipeClient) {
    this.planRepository = planRepository;
    this.recipeClient = recipeClient;
  }
}
