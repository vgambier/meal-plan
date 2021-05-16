package com.vgambier.mealplan.service;

import com.vgambier.mealplan.service.dto.RecipeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vgambier.mealplan.domain.Recipe}.
 */
public interface RecipeService {
    /**
     * Save a recipe.
     *
     * @param recipeDTO the entity to save.
     * @return the persisted entity.
     */
    RecipeDTO save(RecipeDTO recipeDTO);

    /**
     * Partially updates a recipe.
     *
     * @param recipeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RecipeDTO> partialUpdate(RecipeDTO recipeDTO);

    /**
     * Get all the recipes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RecipeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" recipe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecipeDTO> findOne(Long id);

    /**
     * Delete the "id" recipe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
