package com.vgambier.mealplan.service;

import com.vgambier.mealplan.service.dto.RecipeIngredientDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vgambier.mealplan.domain.RecipeIngredient}.
 */
public interface RecipeIngredientService {
    /**
     * Save a recipeIngredient.
     *
     * @param recipeIngredientDTO the entity to save.
     * @return the persisted entity.
     */
    RecipeIngredientDTO save(RecipeIngredientDTO recipeIngredientDTO);

    /**
     * Partially updates a recipeIngredient.
     *
     * @param recipeIngredientDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RecipeIngredientDTO> partialUpdate(RecipeIngredientDTO recipeIngredientDTO);

    /**
     * Get all the recipeIngredients.
     *
     * @return the list of entities.
     */
    List<RecipeIngredientDTO> findAll();

    /**
     * Get the "id" recipeIngredient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecipeIngredientDTO> findOne(Long id);

    /**
     * Delete the "id" recipeIngredient.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
