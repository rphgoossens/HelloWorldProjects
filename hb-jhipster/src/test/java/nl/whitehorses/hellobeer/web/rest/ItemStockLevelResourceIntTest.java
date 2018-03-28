package nl.whitehorses.hellobeer.web.rest;

import nl.whitehorses.hellobeer.HelloBeerApp;

import nl.whitehorses.hellobeer.domain.ItemStockLevel;
import nl.whitehorses.hellobeer.repository.ItemStockLevelRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static nl.whitehorses.hellobeer.web.rest.TestUtil.sameInstant;
import static nl.whitehorses.hellobeer.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ItemStockLevelResource REST controller.
 *
 * @see ItemStockLevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloBeerApp.class)
public class ItemStockLevelResourceIntTest {

    private static final ZonedDateTime DEFAULT_STOCK_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STOCK_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    @Autowired
    private ItemStockLevelRepository itemStockLevelRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restItemStockLevelMockMvc;

    private ItemStockLevel itemStockLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemStockLevelResource itemStockLevelResource = new ItemStockLevelResource(itemStockLevelRepository);
        this.restItemStockLevelMockMvc = MockMvcBuilders.standaloneSetup(itemStockLevelResource)
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
    public static ItemStockLevel createEntity(EntityManager em) {
        ItemStockLevel itemStockLevel = new ItemStockLevel()
            .stockDate(DEFAULT_STOCK_DATE)
            .quantity(DEFAULT_QUANTITY);
        return itemStockLevel;
    }

    @Before
    public void initTest() {
        itemStockLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemStockLevel() throws Exception {
        int databaseSizeBeforeCreate = itemStockLevelRepository.findAll().size();

        // Create the ItemStockLevel
        restItemStockLevelMockMvc.perform(post("/api/item-stock-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemStockLevel)))
            .andExpect(status().isCreated());

        // Validate the ItemStockLevel in the database
        List<ItemStockLevel> itemStockLevelList = itemStockLevelRepository.findAll();
        assertThat(itemStockLevelList).hasSize(databaseSizeBeforeCreate + 1);
        ItemStockLevel testItemStockLevel = itemStockLevelList.get(itemStockLevelList.size() - 1);
        assertThat(testItemStockLevel.getStockDate()).isEqualTo(DEFAULT_STOCK_DATE);
        assertThat(testItemStockLevel.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createItemStockLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemStockLevelRepository.findAll().size();

        // Create the ItemStockLevel with an existing ID
        itemStockLevel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemStockLevelMockMvc.perform(post("/api/item-stock-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemStockLevel)))
            .andExpect(status().isBadRequest());

        // Validate the ItemStockLevel in the database
        List<ItemStockLevel> itemStockLevelList = itemStockLevelRepository.findAll();
        assertThat(itemStockLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStockDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemStockLevelRepository.findAll().size();
        // set the field null
        itemStockLevel.setStockDate(null);

        // Create the ItemStockLevel, which fails.

        restItemStockLevelMockMvc.perform(post("/api/item-stock-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemStockLevel)))
            .andExpect(status().isBadRequest());

        List<ItemStockLevel> itemStockLevelList = itemStockLevelRepository.findAll();
        assertThat(itemStockLevelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemStockLevels() throws Exception {
        // Initialize the database
        itemStockLevelRepository.saveAndFlush(itemStockLevel);

        // Get all the itemStockLevelList
        restItemStockLevelMockMvc.perform(get("/api/item-stock-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemStockLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockDate").value(hasItem(sameInstant(DEFAULT_STOCK_DATE))))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())));
    }

    @Test
    @Transactional
    public void getItemStockLevel() throws Exception {
        // Initialize the database
        itemStockLevelRepository.saveAndFlush(itemStockLevel);

        // Get the itemStockLevel
        restItemStockLevelMockMvc.perform(get("/api/item-stock-levels/{id}", itemStockLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemStockLevel.getId().intValue()))
            .andExpect(jsonPath("$.stockDate").value(sameInstant(DEFAULT_STOCK_DATE)))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItemStockLevel() throws Exception {
        // Get the itemStockLevel
        restItemStockLevelMockMvc.perform(get("/api/item-stock-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemStockLevel() throws Exception {
        // Initialize the database
        itemStockLevelRepository.saveAndFlush(itemStockLevel);
        int databaseSizeBeforeUpdate = itemStockLevelRepository.findAll().size();

        // Update the itemStockLevel
        ItemStockLevel updatedItemStockLevel = itemStockLevelRepository.findOne(itemStockLevel.getId());
        // Disconnect from session so that the updates on updatedItemStockLevel are not directly saved in db
        em.detach(updatedItemStockLevel);
        updatedItemStockLevel
            .stockDate(UPDATED_STOCK_DATE)
            .quantity(UPDATED_QUANTITY);

        restItemStockLevelMockMvc.perform(put("/api/item-stock-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemStockLevel)))
            .andExpect(status().isOk());

        // Validate the ItemStockLevel in the database
        List<ItemStockLevel> itemStockLevelList = itemStockLevelRepository.findAll();
        assertThat(itemStockLevelList).hasSize(databaseSizeBeforeUpdate);
        ItemStockLevel testItemStockLevel = itemStockLevelList.get(itemStockLevelList.size() - 1);
        assertThat(testItemStockLevel.getStockDate()).isEqualTo(UPDATED_STOCK_DATE);
        assertThat(testItemStockLevel.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingItemStockLevel() throws Exception {
        int databaseSizeBeforeUpdate = itemStockLevelRepository.findAll().size();

        // Create the ItemStockLevel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restItemStockLevelMockMvc.perform(put("/api/item-stock-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemStockLevel)))
            .andExpect(status().isCreated());

        // Validate the ItemStockLevel in the database
        List<ItemStockLevel> itemStockLevelList = itemStockLevelRepository.findAll();
        assertThat(itemStockLevelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteItemStockLevel() throws Exception {
        // Initialize the database
        itemStockLevelRepository.saveAndFlush(itemStockLevel);
        int databaseSizeBeforeDelete = itemStockLevelRepository.findAll().size();

        // Get the itemStockLevel
        restItemStockLevelMockMvc.perform(delete("/api/item-stock-levels/{id}", itemStockLevel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ItemStockLevel> itemStockLevelList = itemStockLevelRepository.findAll();
        assertThat(itemStockLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemStockLevel.class);
        ItemStockLevel itemStockLevel1 = new ItemStockLevel();
        itemStockLevel1.setId(1L);
        ItemStockLevel itemStockLevel2 = new ItemStockLevel();
        itemStockLevel2.setId(itemStockLevel1.getId());
        assertThat(itemStockLevel1).isEqualTo(itemStockLevel2);
        itemStockLevel2.setId(2L);
        assertThat(itemStockLevel1).isNotEqualTo(itemStockLevel2);
        itemStockLevel1.setId(null);
        assertThat(itemStockLevel1).isNotEqualTo(itemStockLevel2);
    }
}
