package com.plans_service.entity;

import com.plans_service.enums.RecipeCategory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "app_plan")
public class PlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<PlanRecipeEntity> planRecipes;

    private String username;

    private LocalDate date;

    private Integer consumedCalories;

    public PlanEntity() {
    }

    public Integer getConsumedCalories() {
        return consumedCalories;
    }

    public void setConsumedCalories(Integer consumedCalories) {
        this.consumedCalories = consumedCalories;
    }

    public PlanEntity(List<PlanRecipeEntity> planRecipes, String username, LocalDate date, Integer consumedCalories) {
        this.planRecipes = planRecipes;
        this.username = username;
        this.date = date;
        this.consumedCalories = consumedCalories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PlanRecipeEntity> getPlanRecipes() {
        return planRecipes;
    }

    public void setPlanRecipes(List<PlanRecipeEntity> planRecipes) {
        this.planRecipes = planRecipes;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
