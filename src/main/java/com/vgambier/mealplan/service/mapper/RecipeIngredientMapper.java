package com.vgambier.mealplan.service.mapper;

import com.vgambier.mealplan.domain.*;
import com.vgambier.mealplan.service.dto.RecipeIngredientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RecipeIngredient} and its DTO {@link RecipeIngredientDTO}.
 */
@Mapper(componentModel = "spring", uses = { RecipeMapper.class, IngredientMapper.class })
public interface RecipeIngredientMapper extends EntityMapper<RecipeIngredientDTO, RecipeIngredient> {
    @Mapping(target = "ingredient", source = "ingredient", qualifiedByName = "id")
    RecipeIngredientDTO toDto(RecipeIngredient s);
}
