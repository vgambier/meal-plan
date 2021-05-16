package com.vgambier.mealplan.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecipeServingMapperTest {

    private RecipeServingMapper recipeServingMapper;

    @BeforeEach
    public void setUp() {
        recipeServingMapper = new RecipeServingMapperImpl();
    }
}
