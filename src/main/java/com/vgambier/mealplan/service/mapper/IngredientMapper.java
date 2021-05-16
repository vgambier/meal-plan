package com.vgambier.mealplan.service.mapper;

import com.vgambier.mealplan.domain.*;
import com.vgambier.mealplan.service.dto.IngredientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ingredient} and its DTO {@link IngredientDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IngredientMapper extends EntityMapper<IngredientDTO, Ingredient> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IngredientDTO toDtoId(Ingredient ingredient);
}
