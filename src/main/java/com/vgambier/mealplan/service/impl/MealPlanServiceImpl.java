package com.vgambier.mealplan.service.impl;

import com.vgambier.mealplan.domain.MealPlan;
import com.vgambier.mealplan.repository.MealPlanRepository;
import com.vgambier.mealplan.service.MealPlanService;
import com.vgambier.mealplan.service.dto.MealPlanDTO;
import com.vgambier.mealplan.service.mapper.MealPlanMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MealPlan}.
 */
@Service
@Transactional
public class MealPlanServiceImpl implements MealPlanService {

    private final Logger log = LoggerFactory.getLogger(MealPlanServiceImpl.class);

    private final MealPlanRepository mealPlanRepository;

    private final MealPlanMapper mealPlanMapper;

    public MealPlanServiceImpl(MealPlanRepository mealPlanRepository, MealPlanMapper mealPlanMapper) {
        this.mealPlanRepository = mealPlanRepository;
        this.mealPlanMapper = mealPlanMapper;
    }

    @Override
    public MealPlanDTO save(MealPlanDTO mealPlanDTO) {
        log.debug("Request to save MealPlan : {}", mealPlanDTO);
        MealPlan mealPlan = mealPlanMapper.toEntity(mealPlanDTO);
        mealPlan = mealPlanRepository.save(mealPlan);
        return mealPlanMapper.toDto(mealPlan);
    }

    @Override
    public Optional<MealPlanDTO> partialUpdate(MealPlanDTO mealPlanDTO) {
        log.debug("Request to partially update MealPlan : {}", mealPlanDTO);

        return mealPlanRepository
            .findById(mealPlanDTO.getId())
            .map(
                existingMealPlan -> {
                    mealPlanMapper.partialUpdate(existingMealPlan, mealPlanDTO);
                    return existingMealPlan;
                }
            )
            .map(mealPlanRepository::save)
            .map(mealPlanMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MealPlanDTO> findAll() {
        log.debug("Request to get all MealPlans");
        return mealPlanRepository.findAll().stream().map(mealPlanMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MealPlanDTO> findOne(Long id) {
        log.debug("Request to get MealPlan : {}", id);
        return mealPlanRepository.findById(id).map(mealPlanMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MealPlan : {}", id);
        mealPlanRepository.deleteById(id);
    }
}
