package com.vgambier.mealplan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A RecipeServing.
 */
@Entity
@Table(name = "recipe_serving")
public class RecipeServing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "servings_override")
    private Float servingsOverride;

    @JsonIgnoreProperties(value = { "ingredients" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Recipe recipe;

    @ManyToOne
    @JsonIgnoreProperties(value = { "recipes" }, allowSetters = true)
    private MealPlan mealPlan;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecipeServing id(Long id) {
        this.id = id;
        return this;
    }

    public Float getServingsOverride() {
        return this.servingsOverride;
    }

    public RecipeServing servingsOverride(Float servingsOverride) {
        this.servingsOverride = servingsOverride;
        return this;
    }

    public void setServingsOverride(Float servingsOverride) {
        this.servingsOverride = servingsOverride;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public RecipeServing recipe(Recipe recipe) {
        this.setRecipe(recipe);
        return this;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public MealPlan getMealPlan() {
        return this.mealPlan;
    }

    public RecipeServing mealPlan(MealPlan mealPlan) {
        this.setMealPlan(mealPlan);
        return this;
    }

    public void setMealPlan(MealPlan mealPlan) {
        this.mealPlan = mealPlan;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipeServing)) {
            return false;
        }
        return id != null && id.equals(((RecipeServing) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipeServing{" +
            "id=" + getId() +
            ", servingsOverride=" + getServingsOverride() +
            "}";
    }
}
