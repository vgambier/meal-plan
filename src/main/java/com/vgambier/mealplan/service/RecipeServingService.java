package com.vgambier.mealplan.service;

import com.vgambier.mealplan.service.dto.RecipeServingDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vgambier.mealplan.domain.RecipeServing}.
 */
public interface RecipeServingService {
    /**
     * Save a recipeServing.
     *
     * @param recipeServingDTO the entity to save.
     * @return the persisted entity.
     */
    RecipeServingDTO save(RecipeServingDTO recipeServingDTO);

    /**
     * Partially updates a recipeServing.
     *
     * @param recipeServingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RecipeServingDTO> partialUpdate(RecipeServingDTO recipeServingDTO);

    /**
     * Get all the recipeServings.
     *
     * @return the list of entities.
     */
    List<RecipeServingDTO> findAll();

    /**
     * Get the "id" recipeServing.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecipeServingDTO> findOne(Long id);

    /**
     * Delete the "id" recipeServing.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
