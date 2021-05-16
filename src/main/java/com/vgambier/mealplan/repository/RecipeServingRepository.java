package com.vgambier.mealplan.repository;

import com.vgambier.mealplan.domain.RecipeServing;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RecipeServing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeServingRepository extends JpaRepository<RecipeServing, Long> {}
