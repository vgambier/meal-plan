package com.vgambier.mealplan.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.vgambier.mealplan.domain.RecipeIngredient} entity.
 */
public class RecipeIngredientDTO implements Serializable {

    private Long id;

    private Float quantity;

    private String unit;

    @NotNull
    private Boolean optional;

    private RecipeDTO recipe;

    private IngredientDTO ingredient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public RecipeDTO getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeDTO recipe) {
        this.recipe = recipe;
    }

    public IngredientDTO getIngredient() {
        return ingredient;
    }

    public void setIngredient(IngredientDTO ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipeIngredientDTO)) {
            return false;
        }

        RecipeIngredientDTO recipeIngredientDTO = (RecipeIngredientDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, recipeIngredientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipeIngredientDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unit='" + getUnit() + "'" +
            ", optional='" + getOptional() + "'" +
            ", recipe=" + getRecipe() +
            ", ingredient=" + getIngredient() +
            "}";
    }
}
