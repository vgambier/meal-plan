package com.vgambier.mealplan.service.impl;

import com.vgambier.mealplan.domain.RecipeServing;
import com.vgambier.mealplan.repository.RecipeServingRepository;
import com.vgambier.mealplan.service.RecipeServingService;
import com.vgambier.mealplan.service.dto.RecipeServingDTO;
import com.vgambier.mealplan.service.mapper.RecipeServingMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RecipeServing}.
 */
@Service
@Transactional
public class RecipeServingServiceImpl implements RecipeServingService {

    private final Logger log = LoggerFactory.getLogger(RecipeServingServiceImpl.class);

    private final RecipeServingRepository recipeServingRepository;

    private final RecipeServingMapper recipeServingMapper;

    public RecipeServingServiceImpl(RecipeServingRepository recipeServingRepository, RecipeServingMapper recipeServingMapper) {
        this.recipeServingRepository = recipeServingRepository;
        this.recipeServingMapper = recipeServingMapper;
    }

    @Override
    public RecipeServingDTO save(RecipeServingDTO recipeServingDTO) {
        log.debug("Request to save RecipeServing : {}", recipeServingDTO);
        RecipeServing recipeServing = recipeServingMapper.toEntity(recipeServingDTO);
        recipeServing = recipeServingRepository.save(recipeServing);
        return recipeServingMapper.toDto(recipeServing);
    }

    @Override
    public Optional<RecipeServingDTO> partialUpdate(RecipeServingDTO recipeServingDTO) {
        log.debug("Request to partially update RecipeServing : {}", recipeServingDTO);

        return recipeServingRepository
            .findById(recipeServingDTO.getId())
            .map(
                existingRecipeServing -> {
                    recipeServingMapper.partialUpdate(existingRecipeServing, recipeServingDTO);
                    return existingRecipeServing;
                }
            )
            .map(recipeServingRepository::save)
            .map(recipeServingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeServingDTO> findAll() {
        log.debug("Request to get all RecipeServings");
        return recipeServingRepository.findAll().stream().map(recipeServingMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RecipeServingDTO> findOne(Long id) {
        log.debug("Request to get RecipeServing : {}", id);
        return recipeServingRepository.findById(id).map(recipeServingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RecipeServing : {}", id);
        recipeServingRepository.deleteById(id);
    }
}
