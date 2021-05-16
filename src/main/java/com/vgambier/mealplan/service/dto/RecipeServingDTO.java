package com.vgambier.mealplan.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.vgambier.mealplan.domain.RecipeServing} entity.
 */
public class RecipeServingDTO implements Serializable {

    private Long id;

    private Float servingsOverride;

    private RecipeDTO recipe;

    private MealPlanDTO mealPlan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getServingsOverride() {
        return servingsOverride;
    }

    public void setServingsOverride(Float servingsOverride) {
        this.servingsOverride = servingsOverride;
    }

    public RecipeDTO getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeDTO recipe) {
        this.recipe = recipe;
    }

    public MealPlanDTO getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(MealPlanDTO mealPlan) {
        this.mealPlan = mealPlan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipeServingDTO)) {
            return false;
        }

        RecipeServingDTO recipeServingDTO = (RecipeServingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, recipeServingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipeServingDTO{" +
            "id=" + getId() +
            ", servingsOverride=" + getServingsOverride() +
            ", recipe=" + getRecipe() +
            ", mealPlan=" + getMealPlan() +
            "}";
    }
}
