package com.vgambier.mealplan.service.impl;

import com.vgambier.mealplan.domain.RecipeIngredient;
import com.vgambier.mealplan.repository.RecipeIngredientRepository;
import com.vgambier.mealplan.service.RecipeIngredientService;
import com.vgambier.mealplan.service.dto.RecipeIngredientDTO;
import com.vgambier.mealplan.service.mapper.RecipeIngredientMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RecipeIngredient}.
 */
@Service
@Transactional
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final Logger log = LoggerFactory.getLogger(RecipeIngredientServiceImpl.class);

    private final RecipeIngredientRepository recipeIngredientRepository;

    private final RecipeIngredientMapper recipeIngredientMapper;

    public RecipeIngredientServiceImpl(
        RecipeIngredientRepository recipeIngredientRepository,
        RecipeIngredientMapper recipeIngredientMapper
    ) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeIngredientMapper = recipeIngredientMapper;
    }

    @Override
    public RecipeIngredientDTO save(RecipeIngredientDTO recipeIngredientDTO) {
        log.debug("Request to save RecipeIngredient : {}", recipeIngredientDTO);
        RecipeIngredient recipeIngredient = recipeIngredientMapper.toEntity(recipeIngredientDTO);
        recipeIngredient = recipeIngredientRepository.save(recipeIngredient);
        return recipeIngredientMapper.toDto(recipeIngredient);
    }

    @Override
    public Optional<RecipeIngredientDTO> partialUpdate(RecipeIngredientDTO recipeIngredientDTO) {
        log.debug("Request to partially update RecipeIngredient : {}", recipeIngredientDTO);

        return recipeIngredientRepository
            .findById(recipeIngredientDTO.getId())
            .map(
                existingRecipeIngredient -> {
                    recipeIngredientMapper.partialUpdate(existingRecipeIngredient, recipeIngredientDTO);
                    return existingRecipeIngredient;
                }
            )
            .map(recipeIngredientRepository::save)
            .map(recipeIngredientMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeIngredientDTO> findAll() {
        log.debug("Request to get all RecipeIngredients");
        return recipeIngredientRepository
            .findAll()
            .stream()
            .map(recipeIngredientMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RecipeIngredientDTO> findOne(Long id) {
        log.debug("Request to get RecipeIngredient : {}", id);
        return recipeIngredientRepository.findById(id).map(recipeIngredientMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RecipeIngredient : {}", id);
        recipeIngredientRepository.deleteById(id);
    }
}
