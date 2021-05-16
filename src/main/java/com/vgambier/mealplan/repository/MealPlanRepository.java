package com.vgambier.mealplan.repository;

import com.vgambier.mealplan.domain.MealPlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MealPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {}
