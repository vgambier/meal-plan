package com.vgambier.mealplan.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.vgambier.mealplan.domain.Ingredient} entity.
 */
public class IngredientDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String substitutes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubstitutes() {
        return substitutes;
    }

    public void setSubstitutes(String substitutes) {
        this.substitutes = substitutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IngredientDTO)) {
            return false;
        }

        IngredientDTO ingredientDTO = (IngredientDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ingredientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IngredientDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", substitutes='" + getSubstitutes() + "'" +
            "}";
    }
}
