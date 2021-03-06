package com.vgambier.mealplan.service.mapper;

import com.vgambier.mealplan.domain.*;
import com.vgambier.mealplan.service.dto.RecipeServingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RecipeServing} and its DTO {@link RecipeServingDTO}.
 */
@Mapper(componentModel = "spring", uses = { MealPlanMapper.class, RecipeMapper.class })
public interface RecipeServingMapper extends EntityMapper<RecipeServingDTO, RecipeServing> {
    @Mapping(target = "recipe", source = "recipe", qualifiedByName = "id")
    RecipeServingDTO toDto(RecipeServing s);
}
