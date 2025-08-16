package com.plans_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class PlanDTO {

  private Long id;

  private List<PlanRecipeDTO> planRecipes;

  @NotEmpty(message = "Username cannot be empty")
  private String username;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @NotNull(message = "Date cannot be null")
  private LocalDate date;

  private Integer consumedCalories;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(@NotNull(message = "Date cannot be null") LocalDate date) {
    this.date = date;
  }

  public List<PlanRecipeDTO> getPlanRecipes() {
    return planRecipes;
  }

  public void setPlanRecipes(List<PlanRecipeDTO> planRecipes) {
    this.planRecipes = planRecipes;
  }

  public Integer getConsumedCalories() {
    return consumedCalories;
  }

  public void setConsumedCalories(Integer consumedCalories) {
    this.consumedCalories = consumedCalories;
  }

  public PlanDTO() {
  }

  public PlanDTO(Long id, List<PlanRecipeDTO> planRecipes, String username, LocalDate date, Integer consumedCalories) {
    this.id = id;
    this.planRecipes = planRecipes;
    this.username = username;
    this.date = date;
    this.consumedCalories = consumedCalories;
  }
}