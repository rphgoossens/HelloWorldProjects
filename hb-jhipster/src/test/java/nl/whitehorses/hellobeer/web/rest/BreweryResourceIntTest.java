package nl.whitehorses.hellobeer.web.rest;

import nl.whitehorses.hellobeer.HelloBeerApp;

import nl.whitehorses.hellobeer.domain.Brewery;
import nl.whitehorses.hellobeer.repository.BreweryRepository;
import nl.whitehorses.hellobeer.web.rest.errors.ExceptionTranslator;

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

import static nl.whitehorses.hellobeer.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BreweryResource REST controller.
 *
 * @see BreweryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloBeerApp.class)
public class BreweryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    @Autowired
    private BreweryRepository breweryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBreweryMockMvc;

    private Brewery brewery;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BreweryResource breweryResource = new BreweryResource(breweryRepository);
        this.restBreweryMockMvc = MockMvcBuilders.standaloneSetup(breweryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Brewery createEntity(EntityManager em) {
        Brewery brewery = new Brewery()
            .name(DEFAULT_NAME)
            .countryName(DEFAULT_COUNTRY_NAME);
        return brewery;
    }

    @Before
    public void initTest() {
        brewery = createEntity(em);
    }

    @Test
    @Transactional
    public void createBrewery() throws Exception {
        int databaseSizeBeforeCreate = breweryRepository.findAll().size();

        // Create the Brewery
        restBreweryMockMvc.perform(post("/api/breweries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brewery)))
            .andExpect(status().isCreated());

        // Validate the Brewery in the database
        List<Brewery> breweryList = breweryRepository.findAll();
        assertThat(breweryList).hasSize(databaseSizeBeforeCreate + 1);
        Brewery testBrewery = breweryList.get(breweryList.size() - 1);
        assertThat(testBrewery.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBrewery.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
    }

    @Test
    @Transactional
    public void createBreweryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = breweryRepository.findAll().size();

        // Create the Brewery with an existing ID
        brewery.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBreweryMockMvc.perform(post("/api/breweries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brewery)))
            .andExpect(status().isBadRequest());

        // Validate the Brewery in the database
        List<Brewery> breweryList = breweryRepository.findAll();
        assertThat(breweryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = breweryRepository.findAll().size();
        // set the field null
        brewery.setName(null);

        // Create the Brewery, which fails.

        restBreweryMockMvc.perform(post("/api/breweries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brewery)))
            .andExpect(status().isBadRequest());

        List<Brewery> breweryList = breweryRepository.findAll();
        assertThat(breweryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBreweries() throws Exception {
        // Initialize the database
        breweryRepository.saveAndFlush(brewery);

        // Get all the breweryList
        restBreweryMockMvc.perform(get("/api/breweries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brewery.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME.toString())));
    }

    @Test
    @Transactional
    public void getBrewery() throws Exception {
        // Initialize the database
        breweryRepository.saveAndFlush(brewery);

        // Get the brewery
        restBreweryMockMvc.perform(get("/api/breweries/{id}", brewery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(brewery.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBrewery() throws Exception {
        // Get the brewery
        restBreweryMockMvc.perform(get("/api/breweries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBrewery() throws Exception {
        // Initialize the database
        breweryRepository.saveAndFlush(brewery);
        int databaseSizeBeforeUpdate = breweryRepository.findAll().size();

        // Update the brewery
        Brewery updatedBrewery = breweryRepository.findOne(brewery.getId());
        // Disconnect from session so that the updates on updatedBrewery are not directly saved in db
        em.detach(updatedBrewery);
        updatedBrewery
            .name(UPDATED_NAME)
            .countryName(UPDATED_COUNTRY_NAME);

        restBreweryMockMvc.perform(put("/api/breweries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBrewery)))
            .andExpect(status().isOk());

        // Validate the Brewery in the database
        List<Brewery> breweryList = breweryRepository.findAll();
        assertThat(breweryList).hasSize(databaseSizeBeforeUpdate);
        Brewery testBrewery = breweryList.get(breweryList.size() - 1);
        assertThat(testBrewery.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBrewery.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBrewery() throws Exception {
        int databaseSizeBeforeUpdate = breweryRepository.findAll().size();

        // Create the Brewery

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBreweryMockMvc.perform(put("/api/breweries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brewery)))
            .andExpect(status().isCreated());

        // Validate the Brewery in the database
        List<Brewery> breweryList = breweryRepository.findAll();
        assertThat(breweryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBrewery() throws Exception {
        // Initialize the database
        breweryRepository.saveAndFlush(brewery);
        int databaseSizeBeforeDelete = breweryRepository.findAll().size();

        // Get the brewery
        restBreweryMockMvc.perform(delete("/api/breweries/{id}", brewery.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Brewery> breweryList = breweryRepository.findAll();
        assertThat(breweryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Brewery.class);
        Brewery brewery1 = new Brewery();
        brewery1.setId(1L);
        Brewery brewery2 = new Brewery();
        brewery2.setId(brewery1.getId());
        assertThat(brewery1).isEqualTo(brewery2);
        brewery2.setId(2L);
        assertThat(brewery1).isNotEqualTo(brewery2);
        brewery1.setId(null);
        assertThat(brewery1).isNotEqualTo(brewery2);
    }
}
