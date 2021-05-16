package com.vgambier.mealplan.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgambier.mealplan.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MealPlanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MealPlan.class);
        MealPlan mealPlan1 = new MealPlan();
        mealPlan1.setId(1L);
        MealPlan mealPlan2 = new MealPlan();
        mealPlan2.setId(mealPlan1.getId());
        assertThat(mealPlan1).isEqualTo(mealPlan2);
        mealPlan2.setId(2L);
        assertThat(mealPlan1).isNotEqualTo(mealPlan2);
        mealPlan1.setId(null);
        assertThat(mealPlan1).isNotEqualTo(mealPlan2);
    }
}
