package com.plans_service.entity;

import com.plans_service.enums.RecipeCategory;
import jakarta.persistence.*;

@Entity
@Table(name = "plan_recipe")
public class PlanRecipeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "plan_id")
  private PlanEntity plan;

  @Column(name = "recipe_id")
  private Long recipeId;

  @Column(name = "recipe_category")
  private RecipeCategory recipeCategory;

  public void setPlan(PlanEntity plan) {
    this.plan = plan;
  }

  public RecipeCategory getRecipeCategory() {
    return recipeCategory;
  }

  public void setRecipeCategory(RecipeCategory recipeCategory) {
    this.recipeCategory = recipeCategory;
  }

  public PlanRecipeEntity(PlanEntity plan, Long recipeId, RecipeCategory recipeCategory) {
    this.plan = plan;
    this.recipeId = recipeId;
    this.recipeCategory = recipeCategory;
  }

  public Long getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(Long recipeId) {
    this.recipeId = recipeId;
  }

  public PlanRecipeEntity() {

  }
}

