package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Creneau;

import com.mycompany.myapp.repository.CreneauRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Creneau.
 */
@RestController
@RequestMapping("/api")
public class CreneauResource {

    private final Logger log = LoggerFactory.getLogger(CreneauResource.class);

    private static final String ENTITY_NAME = "creneau";

    private final CreneauRepository creneauRepository;

    public CreneauResource(CreneauRepository creneauRepository) {
        this.creneauRepository = creneauRepository;
    }

    /**
     * POST  /creneaus : Create a new creneau.
     *
     * @param creneau the creneau to create
     * @return the ResponseEntity with status 201 (Created) and with body the new creneau, or with status 400 (Bad Request) if the creneau has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/creneaus")
    @Timed
    public ResponseEntity<Creneau> createCreneau(@RequestBody Creneau creneau) throws URISyntaxException {
        log.debug("REST request to save Creneau : {}", creneau);
        if (creneau.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new creneau cannot already have an ID")).body(null);
        }
        Creneau result = creneauRepository.save(creneau);
        return ResponseEntity.created(new URI("/api/creneaus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /creneaus : Updates an existing creneau.
     *
     * @param creneau the creneau to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated creneau,
     * or with status 400 (Bad Request) if the creneau is not valid,
     * or with status 500 (Internal Server Error) if the creneau couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/creneaus")
    @Timed
    public ResponseEntity<Creneau> updateCreneau(@RequestBody Creneau creneau) throws URISyntaxException {
        log.debug("REST request to update Creneau : {}", creneau);
        if (creneau.getId() == null) {
            return createCreneau(creneau);
        }
        Creneau result = creneauRepository.save(creneau);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, creneau.getId().toString()))
            .body(result);
    }

    /**
     * GET  /creneaus : get all the creneaus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of creneaus in body
     */
    @GetMapping("/creneaus")
    @Timed
    public List<Creneau> getAllCreneaus() {
        log.debug("REST request to get all Creneaus");
        return creneauRepository.findAll();
        }

    /**
     * GET  /creneaus/:id : get the "id" creneau.
     *
     * @param id the id of the creneau to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the creneau, or with status 404 (Not Found)
     */
    @GetMapping("/creneaus/{id}")
    @Timed
    public ResponseEntity<Creneau> getCreneau(@PathVariable Long id) {
        log.debug("REST request to get Creneau : {}", id);
        Creneau creneau = creneauRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(creneau));
    }

    /**
     * DELETE  /creneaus/:id : delete the "id" creneau.
     *
     * @param id the id of the creneau to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/creneaus/{id}")
    @Timed
    public ResponseEntity<Void> deleteCreneau(@PathVariable Long id) {
        log.debug("REST request to delete Creneau : {}", id);
        creneauRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
