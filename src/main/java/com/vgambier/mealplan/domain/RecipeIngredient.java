package com.vgambier.mealplan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A RecipeIngredient.
 */
@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "unit")
    private String unit;

    @NotNull
    @Column(name = "optional", nullable = false)
    private Boolean optional;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    @JsonIgnoreProperties(value = { "recipeServings", "ingredients" }, allowSetters = true)
    private Recipe recipe;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "recipeIngredients" }, allowSetters = true)
    private Ingredient ingredient;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecipeIngredient id(Long id) {
        this.id = id;
        return this;
    }

    public Float getQuantity() {
        return this.quantity;
    }

    public RecipeIngredient quantity(Float quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return this.unit;
    }

    public RecipeIngredient unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getOptional() {
        return this.optional;
    }

    public RecipeIngredient optional(Boolean optional) {
        this.optional = optional;
        return this;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public RecipeIngredient recipe(Recipe recipe) {
        this.setRecipe(recipe);
        return this;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public RecipeIngredient ingredient(Ingredient ingredient) {
        this.setIngredient(ingredient);
        return this;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipeIngredient)) {
            return false;
        }
        return id != null && id.equals(((RecipeIngredient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipeIngredient{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unit='" + getUnit() + "'" +
            ", optional='" + getOptional() + "'" +
            "}";
    }
}
