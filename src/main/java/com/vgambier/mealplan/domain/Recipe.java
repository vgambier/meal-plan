package com.vgambier.mealplan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vgambier.mealplan.domain.enumeration.Season;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A Recipe.
 */
@Entity
@Table(name = "recipe")
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "servings", nullable = false)
    private Float servings;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "instructions", nullable = false)
    private String instructions;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "additional_notes")
    private String additionalNotes;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_content_type")
    private String pictureContentType;

    @Column(name = "source")
    private String source;

    @Enumerated(EnumType.STRING)
    @Column(name = "season")
    private Season season;

    @OneToMany(mappedBy = "recipe")
    @JsonIgnoreProperties(value = { "mealPlan", "recipe" }, allowSetters = true)
    private Set<RecipeServing> recipeServings = new HashSet<>();

    @OneToMany(mappedBy = "recipe")
    @JsonIgnoreProperties(value = { "recipe", "ingredient" }, allowSetters = true)
    private Set<RecipeIngredient> ingredients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Recipe name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getServings() {
        return this.servings;
    }

    public Recipe servings(Float servings) {
        this.servings = servings;
        return this;
    }

    public void setServings(Float servings) {
        this.servings = servings;
    }

    public String getInstructions() {
        return this.instructions;
    }

    public Recipe instructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getAdditionalNotes() {
        return this.additionalNotes;
    }

    public Recipe additionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
        return this;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public byte[] getPicture() {
        return this.picture;
    }

    public Recipe picture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return this.pictureContentType;
    }

    public Recipe pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public String getSource() {
        return this.source;
    }

    public Recipe source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Season getSeason() {
        return this.season;
    }

    public Recipe season(Season season) {
        this.season = season;
        return this;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Set<RecipeServing> getRecipeServings() {
        return this.recipeServings;
    }

    public Recipe recipeServings(Set<RecipeServing> recipeServings) {
        this.setRecipeServings(recipeServings);
        return this;
    }

    public Recipe addRecipeServings(RecipeServing recipeServing) {
        this.recipeServings.add(recipeServing);
        recipeServing.setRecipe(this);
        return this;
    }

    public Recipe removeRecipeServings(RecipeServing recipeServing) {
        this.recipeServings.remove(recipeServing);
        recipeServing.setRecipe(null);
        return this;
    }

    public void setRecipeServings(Set<RecipeServing> recipeServings) {
        if (this.recipeServings != null) {
            this.recipeServings.forEach(i -> i.setRecipe(null));
        }
        if (recipeServings != null) {
            recipeServings.forEach(i -> i.setRecipe(this));
        }
        this.recipeServings = recipeServings;
    }

    public Set<RecipeIngredient> getIngredients() {
        return this.ingredients;
    }

    public Recipe ingredients(Set<RecipeIngredient> recipeIngredients) {
        this.setIngredients(recipeIngredients);
        return this;
    }

    public Recipe addIngredients(RecipeIngredient recipeIngredient) {
        this.ingredients.add(recipeIngredient);
        recipeIngredient.setRecipe(this);
        return this;
    }

    public Recipe removeIngredients(RecipeIngredient recipeIngredient) {
        this.ingredients.remove(recipeIngredient);
        recipeIngredient.setRecipe(null);
        return this;
    }

    public void setIngredients(Set<RecipeIngredient> recipeIngredients) {
        if (this.ingredients != null) {
            this.ingredients.forEach(i -> i.setRecipe(null));
        }
        if (recipeIngredients != null) {
            recipeIngredients.forEach(i -> i.setRecipe(this));
        }
        this.ingredients = recipeIngredients;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recipe)) {
            return false;
        }
        return id != null && id.equals(((Recipe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recipe{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", servings=" + getServings() +
            ", instructions='" + getInstructions() + "'" +
            ", additionalNotes='" + getAdditionalNotes() + "'" +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            ", source='" + getSource() + "'" +
            ", season='" + getSeason() + "'" +
            "}";
    }
}
