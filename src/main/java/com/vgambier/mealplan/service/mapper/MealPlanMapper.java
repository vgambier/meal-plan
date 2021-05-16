package com.vgambier.mealplan.service.mapper;

import com.vgambier.mealplan.domain.*;
import com.vgambier.mealplan.service.dto.MealPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MealPlan} and its DTO {@link MealPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MealPlanMapper extends EntityMapper<MealPlanDTO, MealPlan> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MealPlanDTO toDtoId(MealPlan mealPlan);
}
