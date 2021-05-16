package com.vgambier.mealplan.web.rest;

import com.vgambier.mealplan.repository.MealPlanRepository;
import com.vgambier.mealplan.service.MealPlanService;
import com.vgambier.mealplan.service.dto.MealPlanDTO;
import com.vgambier.mealplan.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.vgambier.mealplan.domain.MealPlan}.
 */
@RestController
@RequestMapping("/api")
public class MealPlanResource {

    private final Logger log = LoggerFactory.getLogger(MealPlanResource.class);

    private static final String ENTITY_NAME = "mealPlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MealPlanService mealPlanService;

    private final MealPlanRepository mealPlanRepository;

    public MealPlanResource(MealPlanService mealPlanService, MealPlanRepository mealPlanRepository) {
        this.mealPlanService = mealPlanService;
        this.mealPlanRepository = mealPlanRepository;
    }

    /**
     * {@code POST  /meal-plans} : Create a new mealPlan.
     *
     * @param mealPlanDTO the mealPlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mealPlanDTO, or with status {@code 400 (Bad Request)} if the mealPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meal-plans")
    public ResponseEntity<MealPlanDTO> createMealPlan(@Valid @RequestBody MealPlanDTO mealPlanDTO) throws URISyntaxException {
        log.debug("REST request to save MealPlan : {}", mealPlanDTO);
        if (mealPlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new mealPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MealPlanDTO result = mealPlanService.save(mealPlanDTO);
        return ResponseEntity
            .created(new URI("/api/meal-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meal-plans/:id} : Updates an existing mealPlan.
     *
     * @param id the id of the mealPlanDTO to save.
     * @param mealPlanDTO the mealPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mealPlanDTO,
     * or with status {@code 400 (Bad Request)} if the mealPlanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mealPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meal-plans/{id}")
    public ResponseEntity<MealPlanDTO> updateMealPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MealPlanDTO mealPlanDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MealPlan : {}, {}", id, mealPlanDTO);
        if (mealPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mealPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mealPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MealPlanDTO result = mealPlanService.save(mealPlanDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mealPlanDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /meal-plans/:id} : Partial updates given fields of an existing mealPlan, field will ignore if it is null
     *
     * @param id the id of the mealPlanDTO to save.
     * @param mealPlanDTO the mealPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mealPlanDTO,
     * or with status {@code 400 (Bad Request)} if the mealPlanDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mealPlanDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mealPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/meal-plans/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MealPlanDTO> partialUpdateMealPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MealPlanDTO mealPlanDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MealPlan partially : {}, {}", id, mealPlanDTO);
        if (mealPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mealPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mealPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MealPlanDTO> result = mealPlanService.partialUpdate(mealPlanDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mealPlanDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /meal-plans} : get all the mealPlans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mealPlans in body.
     */
    @GetMapping("/meal-plans")
    public List<MealPlanDTO> getAllMealPlans() {
        log.debug("REST request to get all MealPlans");
        return mealPlanService.findAll();
    }

    /**
     * {@code GET  /meal-plans/:id} : get the "id" mealPlan.
     *
     * @param id the id of the mealPlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mealPlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meal-plans/{id}")
    public ResponseEntity<MealPlanDTO> getMealPlan(@PathVariable Long id) {
        log.debug("REST request to get MealPlan : {}", id);
        Optional<MealPlanDTO> mealPlanDTO = mealPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mealPlanDTO);
    }

    /**
     * {@code DELETE  /meal-plans/:id} : delete the "id" mealPlan.
     *
     * @param id the id of the mealPlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meal-plans/{id}")
    public ResponseEntity<Void> deleteMealPlan(@PathVariable Long id) {
        log.debug("REST request to delete MealPlan : {}", id);
        mealPlanService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
