package com.vgambier.mealplan.web.rest;

import com.vgambier.mealplan.repository.RecipeServingRepository;
import com.vgambier.mealplan.service.RecipeServingService;
import com.vgambier.mealplan.service.dto.RecipeServingDTO;
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
 * REST controller for managing {@link com.vgambier.mealplan.domain.RecipeServing}.
 */
@RestController
@RequestMapping("/api")
public class RecipeServingResource {

    private final Logger log = LoggerFactory.getLogger(RecipeServingResource.class);

    private static final String ENTITY_NAME = "recipeServing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecipeServingService recipeServingService;

    private final RecipeServingRepository recipeServingRepository;

    public RecipeServingResource(RecipeServingService recipeServingService, RecipeServingRepository recipeServingRepository) {
        this.recipeServingService = recipeServingService;
        this.recipeServingRepository = recipeServingRepository;
    }

    /**
     * {@code POST  /recipe-servings} : Create a new recipeServing.
     *
     * @param recipeServingDTO the recipeServingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recipeServingDTO, or with status {@code 400 (Bad Request)} if the recipeServing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recipe-servings")
    public ResponseEntity<RecipeServingDTO> createRecipeServing(@Valid @RequestBody RecipeServingDTO recipeServingDTO)
        throws URISyntaxException {
        log.debug("REST request to save RecipeServing : {}", recipeServingDTO);
        if (recipeServingDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipeServing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecipeServingDTO result = recipeServingService.save(recipeServingDTO);
        return ResponseEntity
            .created(new URI("/api/recipe-servings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recipe-servings/:id} : Updates an existing recipeServing.
     *
     * @param id the id of the recipeServingDTO to save.
     * @param recipeServingDTO the recipeServingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipeServingDTO,
     * or with status {@code 400 (Bad Request)} if the recipeServingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recipeServingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recipe-servings/{id}")
    public ResponseEntity<RecipeServingDTO> updateRecipeServing(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RecipeServingDTO recipeServingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RecipeServing : {}, {}", id, recipeServingDTO);
        if (recipeServingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recipeServingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recipeServingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RecipeServingDTO result = recipeServingService.save(recipeServingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recipeServingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /recipe-servings/:id} : Partial updates given fields of an existing recipeServing, field will ignore if it is null
     *
     * @param id the id of the recipeServingDTO to save.
     * @param recipeServingDTO the recipeServingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipeServingDTO,
     * or with status {@code 400 (Bad Request)} if the recipeServingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the recipeServingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the recipeServingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/recipe-servings/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RecipeServingDTO> partialUpdateRecipeServing(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RecipeServingDTO recipeServingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RecipeServing partially : {}, {}", id, recipeServingDTO);
        if (recipeServingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recipeServingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recipeServingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecipeServingDTO> result = recipeServingService.partialUpdate(recipeServingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recipeServingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /recipe-servings} : get all the recipeServings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recipeServings in body.
     */
    @GetMapping("/recipe-servings")
    public List<RecipeServingDTO> getAllRecipeServings() {
        log.debug("REST request to get all RecipeServings");
        return recipeServingService.findAll();
    }

    /**
     * {@code GET  /recipe-servings/:id} : get the "id" recipeServing.
     *
     * @param id the id of the recipeServingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recipeServingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recipe-servings/{id}")
    public ResponseEntity<RecipeServingDTO> getRecipeServing(@PathVariable Long id) {
        log.debug("REST request to get RecipeServing : {}", id);
        Optional<RecipeServingDTO> recipeServingDTO = recipeServingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipeServingDTO);
    }

    /**
     * {@code DELETE  /recipe-servings/:id} : delete the "id" recipeServing.
     *
     * @param id the id of the recipeServingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recipe-servings/{id}")
    public ResponseEntity<Void> deleteRecipeServing(@PathVariable Long id) {
        log.debug("REST request to delete RecipeServing : {}", id);
        recipeServingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
