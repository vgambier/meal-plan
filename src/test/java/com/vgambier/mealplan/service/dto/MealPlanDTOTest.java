package com.vgambier.mealplan.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgambier.mealplan.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MealPlanDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MealPlanDTO.class);
        MealPlanDTO mealPlanDTO1 = new MealPlanDTO();
        mealPlanDTO1.setId(1L);
        MealPlanDTO mealPlanDTO2 = new MealPlanDTO();
        assertThat(mealPlanDTO1).isNotEqualTo(mealPlanDTO2);
        mealPlanDTO2.setId(mealPlanDTO1.getId());
        assertThat(mealPlanDTO1).isEqualTo(mealPlanDTO2);
        mealPlanDTO2.setId(2L);
        assertThat(mealPlanDTO1).isNotEqualTo(mealPlanDTO2);
        mealPlanDTO1.setId(null);
        assertThat(mealPlanDTO1).isNotEqualTo(mealPlanDTO2);
    }
}
