package com.vgambier.mealplan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A MealPlan.
 */
@Entity
@Table(name = "meal_plan")
public class MealPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "meal_plan_id")
    @JsonIgnoreProperties(value = { "recipe", "mealPlan" }, allowSetters = true)
    private Set<RecipeServing> recipes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MealPlan id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public MealPlan name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RecipeServing> getRecipes() {
        return this.recipes;
    }

    public MealPlan recipes(Set<RecipeServing> recipeServings) {
        this.setRecipes(recipeServings);
        return this;
    }

    public MealPlan addRecipes(RecipeServing recipeServing) {
        this.recipes.add(recipeServing);
        return this;
    }

    public MealPlan removeRecipes(RecipeServing recipeServing) {
        this.recipes.remove(recipeServing);
        return this;
    }

    public void setRecipes(Set<RecipeServing> recipeServings) {
        this.recipes = recipeServings;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MealPlan)) {
            return false;
        }
        return id != null && id.equals(((MealPlan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MealPlan{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
