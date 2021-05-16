package com.vgambier.mealplan.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgambier.mealplan.IntegrationTest;
import com.vgambier.mealplan.domain.MealPlan;
import com.vgambier.mealplan.domain.RecipeServing;
import com.vgambier.mealplan.repository.MealPlanRepository;
import com.vgambier.mealplan.service.dto.MealPlanDTO;
import com.vgambier.mealplan.service.mapper.MealPlanMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MealPlanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MealPlanResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/meal-plans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private MealPlanMapper mealPlanMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMealPlanMockMvc;

    private MealPlan mealPlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MealPlan createEntity(EntityManager em) {
        MealPlan mealPlan = new MealPlan().name(DEFAULT_NAME);
        // Add required entity
        RecipeServing recipeServing;
        if (TestUtil.findAll(em, RecipeServing.class).isEmpty()) {
            recipeServing = RecipeServingResourceIT.createEntity(em);
            em.persist(recipeServing);
            em.flush();
        } else {
            recipeServing = TestUtil.findAll(em, RecipeServing.class).get(0);
        }
        mealPlan.getRecipes().add(recipeServing);
        return mealPlan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MealPlan createUpdatedEntity(EntityManager em) {
        MealPlan mealPlan = new MealPlan().name(UPDATED_NAME);
        // Add required entity
        RecipeServing recipeServing;
        if (TestUtil.findAll(em, RecipeServing.class).isEmpty()) {
            recipeServing = RecipeServingResourceIT.createUpdatedEntity(em);
            em.persist(recipeServing);
            em.flush();
        } else {
            recipeServing = TestUtil.findAll(em, RecipeServing.class).get(0);
        }
        mealPlan.getRecipes().add(recipeServing);
        return mealPlan;
    }

    @BeforeEach
    public void initTest() {
        mealPlan = createEntity(em);
    }

    @Test
    @Transactional
    void createMealPlan() throws Exception {
        int databaseSizeBeforeCreate = mealPlanRepository.findAll().size();
        // Create the MealPlan
        MealPlanDTO mealPlanDTO = mealPlanMapper.toDto(mealPlan);
        restMealPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MealPlan in the database
        List<MealPlan> mealPlanList = mealPlanRepository.findAll();
        assertThat(mealPlanList).hasSize(databaseSizeBeforeCreate + 1);
        MealPlan testMealPlan = mealPlanList.get(mealPlanList.size() - 1);
        assertThat(testMealPlan.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createMealPlanWithExistingId() throws Exception {
        // Create the MealPlan with an existing ID
        mealPlan.setId(1L);
        MealPlanDTO mealPlanDTO = mealPlanMapper.toDto(mealPlan);

        int databaseSizeBeforeCreate = mealPlanRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMealPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MealPlan in the database
        List<MealPlan> mealPlanList = mealPlanRepository.findAll();
        assertThat(mealPlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mealPlanRepository.findAll().size();
        // set the field null
        mealPlan.setName(null);

        // Create the MealPlan, which fails.
        MealPlanDTO mealPlanDTO = mealPlanMapper.toDto(mealPlan);

        restMealPlanMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanDTO))
            )
            .andExpect(status().isBadRequest());

        List<MealPlan> mealPlanList = mealPlanRepository.findAll();
        assertThat(mealPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMealPlans() throws Exception {
        // Initialize the database
        mealPlanRepository.saveAndFlush(mealPlan);

        // Get all the mealPlanList
        restMealPlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mealPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getMealPlan() throws Exception {
        // Initialize the database
        mealPlanRepository.saveAndFlush(mealPlan);

        // Get the mealPlan
        restMealPlanMockMvc
            .perform(get(ENTITY_API_URL_ID, mealPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mealPlan.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingMealPlan() throws Exception {
        // Get the mealPlan
        restMealPlanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMealPlan() throws Exception {
        // Initialize the database
        mealPlanRepository.saveAndFlush(mealPlan);

        int databaseSizeBeforeUpdate = mealPlanRepository.findAll().size();

        // Update the mealPlan
        MealPlan updatedMealPlan = mealPlanRepository.findById(mealPlan.getId()).get();
        // Disconnect from session so that the updates on updatedMealPlan are not directly saved in db
        em.detach(updatedMealPlan);
        updatedMealPlan.name(UPDATED_NAME);
        MealPlanDTO mealPlanDTO = mealPlanMapper.toDto(updatedMealPlan);

        restMealPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mealPlanDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanDTO))
            )
            .andExpect(status().isOk());

        // Validate the MealPlan in the database
        List<MealPlan> mealPlanList = mealPlanRepository.findAll();
        assertThat(mealPlanList).hasSize(databaseSizeBeforeUpdate);
        MealPlan testMealPlan = mealPlanList.get(mealPlanList.size() - 1);
        assertThat(testMealPlan.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingMealPlan() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanRepository.findAll().size();
        mealPlan.setId(count.incrementAndGet());

        // Create the MealPlan
        MealPlanDTO mealPlanDTO = mealPlanMapper.toDto(mealPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMealPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mealPlanDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MealPlan in the database
        List<MealPlan> mealPlanList = mealPlanRepository.findAll();
        assertThat(mealPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMealPlan() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanRepository.findAll().size();
        mealPlan.setId(count.incrementAndGet());

        // Create the MealPlan
        MealPlanDTO mealPlanDTO = mealPlanMapper.toDto(mealPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMealPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MealPlan in the database
        List<MealPlan> mealPlanList = mealPlanRepository.findAll();
        assertThat(mealPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMealPlan() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanRepository.findAll().size();
        mealPlan.setId(count.incrementAndGet());

        // Create the MealPlan
        MealPlanDTO mealPlanDTO = mealPlanMapper.toDto(mealPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMealPlanMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MealPlan in the database
        List<MealPlan> mealPlanList = mealPlanRepository.findAll();
        assertThat(mealPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMealPlanWithPatch() throws Exception {
        // Initialize the database
        mealPlanRepository.saveAndFlush(mealPlan);

        int databaseSizeBeforeUpdate = mealPlanRepository.findAll().size();

        // Update the mealPlan using partial update
        MealPlan partialUpdatedMealPlan = new MealPlan();
        partialUpdatedMealPlan.setId(mealPlan.getId());

        partialUpdatedMealPlan.name(UPDATED_NAME);

        restMealPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMealPlan.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMealPlan))
            )
            .andExpect(status().isOk());

        // Validate the MealPlan in the database
        List<MealPlan> mealPlanList = mealPlanRepository.findAll();
        assertThat(mealPlanList).hasSize(databaseSizeBeforeUpdate);
        MealPlan testMealPlan = mealPlanList.get(mealPlanList.size() - 1);
        assertThat(testMealPlan.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateMealPlanWithPatch() throws Exception {
        // Initialize the database
        mealPlanRepository.saveAndFlush(mealPlan);

        int databaseSizeBeforeUpdate = mealPlanRepository.findAll().size();

        // Update the mealPlan using partial update
        MealPlan partialUpdatedMealPlan = new MealPlan();
        partialUpdatedMealPlan.setId(mealPlan.getId());

        partialUpdatedMealPlan.name(UPDATED_NAME);

        restMealPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMealPlan.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMealPlan))
            )
            .andExpect(status().isOk());

        // Validate the MealPlan in the database
        List<MealPlan> mealPlanList = mealPlanRepository.findAll();
        assertThat(mealPlanList).hasSize(databaseSizeBeforeUpdate);
        MealPlan testMealPlan = mealPlanList.get(mealPlanList.size() - 1);
        assertThat(testMealPlan.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingMealPlan() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanRepository.findAll().size();
        mealPlan.setId(count.incrementAndGet());

        // Create the MealPlan
        MealPlanDTO mealPlanDTO = mealPlanMapper.toDto(mealPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMealPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mealPlanDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MealPlan in the database
        List<MealPlan> mealPlanList = mealPlanRepository.findAll();
        assertThat(mealPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMealPlan() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanRepository.findAll().size();
        mealPlan.setId(count.incrementAndGet());

        // Create the MealPlan
        MealPlanDTO mealPlanDTO = mealPlanMapper.toDto(mealPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMealPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MealPlan in the database
        List<MealPlan> mealPlanList = mealPlanRepository.findAll();
        assertThat(mealPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMealPlan() throws Exception {
        int databaseSizeBeforeUpdate = mealPlanRepository.findAll().size();
        mealPlan.setId(count.incrementAndGet());

        // Create the MealPlan
        MealPlanDTO mealPlanDTO = mealPlanMapper.toDto(mealPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMealPlanMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mealPlanDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MealPlan in the database
        List<MealPlan> mealPlanList = mealPlanRepository.findAll();
        assertThat(mealPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMealPlan() throws Exception {
        // Initialize the database
        mealPlanRepository.saveAndFlush(mealPlan);

        int databaseSizeBeforeDelete = mealPlanRepository.findAll().size();

        // Delete the mealPlan
        restMealPlanMockMvc
            .perform(delete(ENTITY_API_URL_ID, mealPlan.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MealPlan> mealPlanList = mealPlanRepository.findAll();
        assertThat(mealPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
