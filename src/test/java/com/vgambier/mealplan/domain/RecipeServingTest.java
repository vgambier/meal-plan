package com.vgambier.mealplan.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vgambier.mealplan.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecipeServingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeServing.class);
        RecipeServing recipeServing1 = new RecipeServing();
        recipeServing1.setId(1L);
        RecipeServing recipeServing2 = new RecipeServing();
        recipeServing2.setId(recipeServing1.getId());
        assertThat(recipeServing1).isEqualTo(recipeServing2);
        recipeServing2.setId(2L);
        assertThat(recipeServing1).isNotEqualTo(recipeServing2);
        recipeServing1.setId(null);
        assertThat(recipeServing1).isNotEqualTo(recipeServing2);
    }
}
