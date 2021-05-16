package com.vgambier.mealplan.service.impl;

import com.vgambier.mealplan.domain.Recipe;
import com.vgambier.mealplan.repository.RecipeRepository;
import com.vgambier.mealplan.service.RecipeService;
import com.vgambier.mealplan.service.dto.RecipeDTO;
import com.vgambier.mealplan.service.mapper.RecipeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Recipe}.
 */
@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {

    private final Logger log = LoggerFactory.getLogger(RecipeServiceImpl.class);

    private final RecipeRepository recipeRepository;

    private final RecipeMapper recipeMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public RecipeDTO save(RecipeDTO recipeDTO) {
        log.debug("Request to save Recipe : {}", recipeDTO);
        Recipe recipe = recipeMapper.toEntity(recipeDTO);
        recipe = recipeRepository.save(recipe);
        return recipeMapper.toDto(recipe);
    }

    @Override
    public Optional<RecipeDTO> partialUpdate(RecipeDTO recipeDTO) {
        log.debug("Request to partially update Recipe : {}", recipeDTO);

        return recipeRepository
            .findById(recipeDTO.getId())
            .map(
                existingRecipe -> {
                    recipeMapper.partialUpdate(existingRecipe, recipeDTO);
                    return existingRecipe;
                }
            )
            .map(recipeRepository::save)
            .map(recipeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecipeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recipes");
        return recipeRepository.findAll(pageable).map(recipeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RecipeDTO> findOne(Long id) {
        log.debug("Request to get Recipe : {}", id);
        return recipeRepository.findById(id).map(recipeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Recipe : {}", id);
        recipeRepository.deleteById(id);
    }
}
