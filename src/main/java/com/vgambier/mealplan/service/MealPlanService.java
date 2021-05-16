package com.vgambier.mealplan.service;

import com.vgambier.mealplan.service.dto.MealPlanDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vgambier.mealplan.domain.MealPlan}.
 */
public interface MealPlanService {
    /**
     * Save a mealPlan.
     *
     * @param mealPlanDTO the entity to save.
     * @return the persisted entity.
     */
    MealPlanDTO save(MealPlanDTO mealPlanDTO);

    /**
     * Partially updates a mealPlan.
     *
     * @param mealPlanDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MealPlanDTO> partialUpdate(MealPlanDTO mealPlanDTO);

    /**
     * Get all the mealPlans.
     *
     * @return the list of entities.
     */
    List<MealPlanDTO> findAll();

    /**
     * Get the "id" mealPlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MealPlanDTO> findOne(Long id);

    /**
     * Delete the "id" mealPlan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
