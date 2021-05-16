package com.vgambier.mealplan.repository;

import com.vgambier.mealplan.domain.RecipeIngredient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RecipeIngredient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {}
