package com.vgambier.mealplan.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgambier.mealplan.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecipeServingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeServingDTO.class);
        RecipeServingDTO recipeServingDTO1 = new RecipeServingDTO();
        recipeServingDTO1.setId(1L);
        RecipeServingDTO recipeServingDTO2 = new RecipeServingDTO();
        assertThat(recipeServingDTO1).isNotEqualTo(recipeServingDTO2);
        recipeServingDTO2.setId(recipeServingDTO1.getId());
        assertThat(recipeServingDTO1).isEqualTo(recipeServingDTO2);
        recipeServingDTO2.setId(2L);
        assertThat(recipeServingDTO1).isNotEqualTo(recipeServingDTO2);
        recipeServingDTO1.setId(null);
        assertThat(recipeServingDTO1).isNotEqualTo(recipeServingDTO2);
    }
}
