package com.plans_service.dto;


import com.plans_service.enums.RecipeCategory;

public class PlanRecipeDTO {

  private Long  recipeId;
  private Integer calories;
  private RecipeCategory recipeCategory;

  public PlanRecipeDTO(Long recipeId, Integer calories, RecipeCategory recipeCategory) {
    this.recipeId = recipeId;
    this.calories = calories;
    this.recipeCategory = recipeCategory;
  }

  public PlanRecipeDTO() {}

  public Long getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(Long recipeId) {
    this.recipeId = recipeId;
  }

  public Integer getCalories() {
    return calories;
  }

  public void setCalories(Integer calories) {
    this.calories = calories;
  }

  public RecipeCategory getRecipeCategory() {
    return recipeCategory;
  }

  public void setRecipeCategory(RecipeCategory recipeCategory) {
    this.recipeCategory = recipeCategory;
  }
}
