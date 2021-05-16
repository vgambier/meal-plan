package com.vgambier.mealplan.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgambier.mealplan.IntegrationTest;
import com.vgambier.mealplan.domain.Recipe;
import com.vgambier.mealplan.domain.RecipeServing;
import com.vgambier.mealplan.repository.RecipeServingRepository;
import com.vgambier.mealplan.service.dto.RecipeServingDTO;
import com.vgambier.mealplan.service.mapper.RecipeServingMapper;
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
 * Integration tests for the {@link RecipeServingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RecipeServingResourceIT {

    private static final Float DEFAULT_SERVINGS_OVERRIDE = 1F;
    private static final Float UPDATED_SERVINGS_OVERRIDE = 2F;

    private static final String ENTITY_API_URL = "/api/recipe-servings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RecipeServingRepository recipeServingRepository;

    @Autowired
    private RecipeServingMapper recipeServingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecipeServingMockMvc;

    private RecipeServing recipeServing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeServing createEntity(EntityManager em) {
        RecipeServing recipeServing = new RecipeServing().servingsOverride(DEFAULT_SERVINGS_OVERRIDE);
        // Add required entity
        Recipe recipe;
        if (TestUtil.findAll(em, Recipe.class).isEmpty()) {
            recipe = RecipeResourceIT.createEntity(em);
            em.persist(recipe);
            em.flush();
        } else {
            recipe = TestUtil.findAll(em, Recipe.class).get(0);
        }
        recipeServing.setRecipe(recipe);
        return recipeServing;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeServing createUpdatedEntity(EntityManager em) {
        RecipeServing recipeServing = new RecipeServing().servingsOverride(UPDATED_SERVINGS_OVERRIDE);
        // Add required entity
        Recipe recipe;
        if (TestUtil.findAll(em, Recipe.class).isEmpty()) {
            recipe = RecipeResourceIT.createUpdatedEntity(em);
            em.persist(recipe);
            em.flush();
        } else {
            recipe = TestUtil.findAll(em, Recipe.class).get(0);
        }
        recipeServing.setRecipe(recipe);
        return recipeServing;
    }

    @BeforeEach
    public void initTest() {
        recipeServing = createEntity(em);
    }

    @Test
    @Transactional
    void createRecipeServing() throws Exception {
        int databaseSizeBeforeCreate = recipeServingRepository.findAll().size();
        // Create the RecipeServing
        RecipeServingDTO recipeServingDTO = recipeServingMapper.toDto(recipeServing);
        restRecipeServingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recipeServingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RecipeServing in the database
        List<RecipeServing> recipeServingList = recipeServingRepository.findAll();
        assertThat(recipeServingList).hasSize(databaseSizeBeforeCreate + 1);
        RecipeServing testRecipeServing = recipeServingList.get(recipeServingList.size() - 1);
        assertThat(testRecipeServing.getServingsOverride()).isEqualTo(DEFAULT_SERVINGS_OVERRIDE);
    }

    @Test
    @Transactional
    void createRecipeServingWithExistingId() throws Exception {
        // Create the RecipeServing with an existing ID
        recipeServing.setId(1L);
        RecipeServingDTO recipeServingDTO = recipeServingMapper.toDto(recipeServing);

        int databaseSizeBeforeCreate = recipeServingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeServingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recipeServingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeServing in the database
        List<RecipeServing> recipeServingList = recipeServingRepository.findAll();
        assertThat(recipeServingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRecipeServings() throws Exception {
        // Initialize the database
        recipeServingRepository.saveAndFlush(recipeServing);

        // Get all the recipeServingList
        restRecipeServingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeServing.getId().intValue())))
            .andExpect(jsonPath("$.[*].servingsOverride").value(hasItem(DEFAULT_SERVINGS_OVERRIDE.doubleValue())));
    }

    @Test
    @Transactional
    void getRecipeServing() throws Exception {
        // Initialize the database
        recipeServingRepository.saveAndFlush(recipeServing);

        // Get the recipeServing
        restRecipeServingMockMvc
            .perform(get(ENTITY_API_URL_ID, recipeServing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recipeServing.getId().intValue()))
            .andExpect(jsonPath("$.servingsOverride").value(DEFAULT_SERVINGS_OVERRIDE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingRecipeServing() throws Exception {
        // Get the recipeServing
        restRecipeServingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRecipeServing() throws Exception {
        // Initialize the database
        recipeServingRepository.saveAndFlush(recipeServing);

        int databaseSizeBeforeUpdate = recipeServingRepository.findAll().size();

        // Update the recipeServing
        RecipeServing updatedRecipeServing = recipeServingRepository.findById(recipeServing.getId()).get();
        // Disconnect from session so that the updates on updatedRecipeServing are not directly saved in db
        em.detach(updatedRecipeServing);
        updatedRecipeServing.servingsOverride(UPDATED_SERVINGS_OVERRIDE);
        RecipeServingDTO recipeServingDTO = recipeServingMapper.toDto(updatedRecipeServing);

        restRecipeServingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recipeServingDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recipeServingDTO))
            )
            .andExpect(status().isOk());

        // Validate the RecipeServing in the database
        List<RecipeServing> recipeServingList = recipeServingRepository.findAll();
        assertThat(recipeServingList).hasSize(databaseSizeBeforeUpdate);
        RecipeServing testRecipeServing = recipeServingList.get(recipeServingList.size() - 1);
        assertThat(testRecipeServing.getServingsOverride()).isEqualTo(UPDATED_SERVINGS_OVERRIDE);
    }

    @Test
    @Transactional
    void putNonExistingRecipeServing() throws Exception {
        int databaseSizeBeforeUpdate = recipeServingRepository.findAll().size();
        recipeServing.setId(count.incrementAndGet());

        // Create the RecipeServing
        RecipeServingDTO recipeServingDTO = recipeServingMapper.toDto(recipeServing);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeServingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recipeServingDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recipeServingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeServing in the database
        List<RecipeServing> recipeServingList = recipeServingRepository.findAll();
        assertThat(recipeServingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecipeServing() throws Exception {
        int databaseSizeBeforeUpdate = recipeServingRepository.findAll().size();
        recipeServing.setId(count.incrementAndGet());

        // Create the RecipeServing
        RecipeServingDTO recipeServingDTO = recipeServingMapper.toDto(recipeServing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeServingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recipeServingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeServing in the database
        List<RecipeServing> recipeServingList = recipeServingRepository.findAll();
        assertThat(recipeServingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecipeServing() throws Exception {
        int databaseSizeBeforeUpdate = recipeServingRepository.findAll().size();
        recipeServing.setId(count.incrementAndGet());

        // Create the RecipeServing
        RecipeServingDTO recipeServingDTO = recipeServingMapper.toDto(recipeServing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeServingMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recipeServingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecipeServing in the database
        List<RecipeServing> recipeServingList = recipeServingRepository.findAll();
        assertThat(recipeServingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecipeServingWithPatch() throws Exception {
        // Initialize the database
        recipeServingRepository.saveAndFlush(recipeServing);

        int databaseSizeBeforeUpdate = recipeServingRepository.findAll().size();

        // Update the recipeServing using partial update
        RecipeServing partialUpdatedRecipeServing = new RecipeServing();
        partialUpdatedRecipeServing.setId(recipeServing.getId());

        partialUpdatedRecipeServing.servingsOverride(UPDATED_SERVINGS_OVERRIDE);

        restRecipeServingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecipeServing.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecipeServing))
            )
            .andExpect(status().isOk());

        // Validate the RecipeServing in the database
        List<RecipeServing> recipeServingList = recipeServingRepository.findAll();
        assertThat(recipeServingList).hasSize(databaseSizeBeforeUpdate);
        RecipeServing testRecipeServing = recipeServingList.get(recipeServingList.size() - 1);
        assertThat(testRecipeServing.getServingsOverride()).isEqualTo(UPDATED_SERVINGS_OVERRIDE);
    }

    @Test
    @Transactional
    void fullUpdateRecipeServingWithPatch() throws Exception {
        // Initialize the database
        recipeServingRepository.saveAndFlush(recipeServing);

        int databaseSizeBeforeUpdate = recipeServingRepository.findAll().size();

        // Update the recipeServing using partial update
        RecipeServing partialUpdatedRecipeServing = new RecipeServing();
        partialUpdatedRecipeServing.setId(recipeServing.getId());

        partialUpdatedRecipeServing.servingsOverride(UPDATED_SERVINGS_OVERRIDE);

        restRecipeServingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecipeServing.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecipeServing))
            )
            .andExpect(status().isOk());

        // Validate the RecipeServing in the database
        List<RecipeServing> recipeServingList = recipeServingRepository.findAll();
        assertThat(recipeServingList).hasSize(databaseSizeBeforeUpdate);
        RecipeServing testRecipeServing = recipeServingList.get(recipeServingList.size() - 1);
        assertThat(testRecipeServing.getServingsOverride()).isEqualTo(UPDATED_SERVINGS_OVERRIDE);
    }

    @Test
    @Transactional
    void patchNonExistingRecipeServing() throws Exception {
        int databaseSizeBeforeUpdate = recipeServingRepository.findAll().size();
        recipeServing.setId(count.incrementAndGet());

        // Create the RecipeServing
        RecipeServingDTO recipeServingDTO = recipeServingMapper.toDto(recipeServing);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeServingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recipeServingDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recipeServingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeServing in the database
        List<RecipeServing> recipeServingList = recipeServingRepository.findAll();
        assertThat(recipeServingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecipeServing() throws Exception {
        int databaseSizeBeforeUpdate = recipeServingRepository.findAll().size();
        recipeServing.setId(count.incrementAndGet());

        // Create the RecipeServing
        RecipeServingDTO recipeServingDTO = recipeServingMapper.toDto(recipeServing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeServingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recipeServingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecipeServing in the database
        List<RecipeServing> recipeServingList = recipeServingRepository.findAll();
        assertThat(recipeServingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecipeServing() throws Exception {
        int databaseSizeBeforeUpdate = recipeServingRepository.findAll().size();
        recipeServing.setId(count.incrementAndGet());

        // Create the RecipeServing
        RecipeServingDTO recipeServingDTO = recipeServingMapper.toDto(recipeServing);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecipeServingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recipeServingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecipeServing in the database
        List<RecipeServing> recipeServingList = recipeServingRepository.findAll();
        assertThat(recipeServingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecipeServing() throws Exception {
        // Initialize the database
        recipeServingRepository.saveAndFlush(recipeServing);

        int databaseSizeBeforeDelete = recipeServingRepository.findAll().size();

        // Delete the recipeServing
        restRecipeServingMockMvc
            .perform(delete(ENTITY_API_URL_ID, recipeServing.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecipeServing> recipeServingList = recipeServingRepository.findAll();
        assertThat(recipeServingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
