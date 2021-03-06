package com.vgambier.mealplan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Ingredient.
 */
@Entity
@Table(name = "ingredient")
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "substitutes")
    private String substitutes;

    @OneToMany(mappedBy = "ingredient")
    @JsonIgnoreProperties(value = { "recipe", "ingredient" }, allowSetters = true)
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ingredient id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Ingredient name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubstitutes() {
        return this.substitutes;
    }

    public Ingredient substitutes(String substitutes) {
        this.substitutes = substitutes;
        return this;
    }

    public void setSubstitutes(String substitutes) {
        this.substitutes = substitutes;
    }

    public Set<RecipeIngredient> getRecipeIngredients() {
        return this.recipeIngredients;
    }

    public Ingredient recipeIngredients(Set<RecipeIngredient> recipeIngredients) {
        this.setRecipeIngredients(recipeIngredients);
        return this;
    }

    public Ingredient addRecipeIngredients(RecipeIngredient recipeIngredient) {
        this.recipeIngredients.add(recipeIngredient);
        recipeIngredient.setIngredient(this);
        return this;
    }

    public Ingredient removeRecipeIngredients(RecipeIngredient recipeIngredient) {
        this.recipeIngredients.remove(recipeIngredient);
        recipeIngredient.setIngredient(null);
        return this;
    }

    public void setRecipeIngredients(Set<RecipeIngredient> recipeIngredients) {
        if (this.recipeIngredients != null) {
            this.recipeIngredients.forEach(i -> i.setIngredient(null));
        }
        if (recipeIngredients != null) {
            recipeIngredients.forEach(i -> i.setIngredient(this));
        }
        this.recipeIngredients = recipeIngredients;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ingredient)) {
            return false;
        }
        return id != null && id.equals(((Ingredient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ingredient{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", substitutes='" + getSubstitutes() + "'" +
            "}";
    }
}
