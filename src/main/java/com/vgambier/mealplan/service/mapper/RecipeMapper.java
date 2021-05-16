package com.vgambier.mealplan.service.mapper;

import com.vgambier.mealplan.domain.*;
import com.vgambier.mealplan.service.dto.RecipeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Recipe} and its DTO {@link RecipeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RecipeMapper extends EntityMapper<RecipeDTO, Recipe> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RecipeDTO toDtoId(Recipe recipe);
}
