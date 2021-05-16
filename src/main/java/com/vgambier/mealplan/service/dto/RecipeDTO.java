package com.vgambier.mealplan.service.dto;

import com.vgambier.mealplan.domain.enumeration.Season;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.vgambier.mealplan.domain.Recipe} entity.
 */
public class RecipeDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Float servings;

    @Lob
    private String instructions;

    @Lob
    private String additionalNotes;

    @Lob
    private byte[] picture;

    private String pictureContentType;
    private String source;

    private Season season;

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

    public Float getServings() {
        return servings;
    }

    public void setServings(Float servings) {
        this.servings = servings;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipeDTO)) {
            return false;
        }

        RecipeDTO recipeDTO = (RecipeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, recipeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", servings=" + getServings() +
            ", instructions='" + getInstructions() + "'" +
            ", additionalNotes='" + getAdditionalNotes() + "'" +
            ", picture='" + getPicture() + "'" +
            ", source='" + getSource() + "'" +
            ", season='" + getSeason() + "'" +
            "}";
    }
}
