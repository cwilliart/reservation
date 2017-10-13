package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ReservationApp;

import com.mycompany.myapp.domain.Creneau;
import com.mycompany.myapp.repository.CreneauRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CreneauResource REST controller.
 *
 * @see CreneauResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReservationApp.class)
public class CreneauResourceIntTest {

    private static final String DEFAULT_HEURE = "AAAAAAAAAA";
    private static final String UPDATED_HEURE = "BBBBBBBBBB";

    @Autowired
    private CreneauRepository creneauRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCreneauMockMvc;

    private Creneau creneau;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CreneauResource creneauResource = new CreneauResource(creneauRepository);
        this.restCreneauMockMvc = MockMvcBuilders.standaloneSetup(creneauResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Creneau createEntity(EntityManager em) {
        Creneau creneau = new Creneau()
            .heure(DEFAULT_HEURE);
        return creneau;
    }

    @Before
    public void initTest() {
        creneau = createEntity(em);
    }

    @Test
    @Transactional
    public void createCreneau() throws Exception {
        int databaseSizeBeforeCreate = creneauRepository.findAll().size();

        // Create the Creneau
        restCreneauMockMvc.perform(post("/api/creneaus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creneau)))
            .andExpect(status().isCreated());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeCreate + 1);
        Creneau testCreneau = creneauList.get(creneauList.size() - 1);
        assertThat(testCreneau.getHeure()).isEqualTo(DEFAULT_HEURE);
    }

    @Test
    @Transactional
    public void createCreneauWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = creneauRepository.findAll().size();

        // Create the Creneau with an existing ID
        creneau.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreneauMockMvc.perform(post("/api/creneaus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creneau)))
            .andExpect(status().isBadRequest());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCreneaus() throws Exception {
        // Initialize the database
        creneauRepository.saveAndFlush(creneau);

        // Get all the creneauList
        restCreneauMockMvc.perform(get("/api/creneaus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creneau.getId().intValue())))
            .andExpect(jsonPath("$.[*].heure").value(hasItem(DEFAULT_HEURE.toString())));
    }

    @Test
    @Transactional
    public void getCreneau() throws Exception {
        // Initialize the database
        creneauRepository.saveAndFlush(creneau);

        // Get the creneau
        restCreneauMockMvc.perform(get("/api/creneaus/{id}", creneau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(creneau.getId().intValue()))
            .andExpect(jsonPath("$.heure").value(DEFAULT_HEURE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCreneau() throws Exception {
        // Get the creneau
        restCreneauMockMvc.perform(get("/api/creneaus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCreneau() throws Exception {
        // Initialize the database
        creneauRepository.saveAndFlush(creneau);
        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();

        // Update the creneau
        Creneau updatedCreneau = creneauRepository.findOne(creneau.getId());
        updatedCreneau
            .heure(UPDATED_HEURE);

        restCreneauMockMvc.perform(put("/api/creneaus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCreneau)))
            .andExpect(status().isOk());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate);
        Creneau testCreneau = creneauList.get(creneauList.size() - 1);
        assertThat(testCreneau.getHeure()).isEqualTo(UPDATED_HEURE);
    }

    @Test
    @Transactional
    public void updateNonExistingCreneau() throws Exception {
        int databaseSizeBeforeUpdate = creneauRepository.findAll().size();

        // Create the Creneau

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCreneauMockMvc.perform(put("/api/creneaus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creneau)))
            .andExpect(status().isCreated());

        // Validate the Creneau in the database
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCreneau() throws Exception {
        // Initialize the database
        creneauRepository.saveAndFlush(creneau);
        int databaseSizeBeforeDelete = creneauRepository.findAll().size();

        // Get the creneau
        restCreneauMockMvc.perform(delete("/api/creneaus/{id}", creneau.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Creneau> creneauList = creneauRepository.findAll();
        assertThat(creneauList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Creneau.class);
        Creneau creneau1 = new Creneau();
        creneau1.setId(1L);
        Creneau creneau2 = new Creneau();
        creneau2.setId(creneau1.getId());
        assertThat(creneau1).isEqualTo(creneau2);
        creneau2.setId(2L);
        assertThat(creneau1).isNotEqualTo(creneau2);
        creneau1.setId(null);
        assertThat(creneau1).isNotEqualTo(creneau2);
    }
}
