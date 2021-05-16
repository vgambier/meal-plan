package com.vgambier.mealplan.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.vgambier.mealplan.domain.MealPlan} entity.
 */
public class MealPlanDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MealPlanDTO)) {
            return false;
        }

        MealPlanDTO mealPlanDTO = (MealPlanDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mealPlanDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MealPlanDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
