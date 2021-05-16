package com.vgambier.mealplan.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MealPlanMapperTest {

    private MealPlanMapper mealPlanMapper;

    @BeforeEach
    public void setUp() {
        mealPlanMapper = new MealPlanMapperImpl();
    }
}
