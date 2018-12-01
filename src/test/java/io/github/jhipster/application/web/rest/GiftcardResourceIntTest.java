package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterTzApplicationApp;

import io.github.jhipster.application.domain.Giftcard;
import io.github.jhipster.application.repository.GiftcardRepository;
import io.github.jhipster.application.repository.search.GiftcardSearchRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GiftcardResource REST controller.
 *
 * @see GiftcardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterTzApplicationApp.class)
public class GiftcardResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final Integer DEFAULT_GIFTCARD_TYPE_ID = 1;
    private static final Integer UPDATED_GIFTCARD_TYPE_ID = 2;

    @Autowired
    private GiftcardRepository giftcardRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.GiftcardSearchRepositoryMockConfiguration
     */
    @Autowired
    private GiftcardSearchRepository mockGiftcardSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGiftcardMockMvc;

    private Giftcard giftcard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GiftcardResource giftcardResource = new GiftcardResource(giftcardRepository, mockGiftcardSearchRepository);
        this.restGiftcardMockMvc = MockMvcBuilders.standaloneSetup(giftcardResource)
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
    public static Giftcard createEntity(EntityManager em) {
        Giftcard giftcard = new Giftcard()
            .name(DEFAULT_NAME)
            .amount(DEFAULT_AMOUNT)
            .unit(DEFAULT_UNIT)
            .giftcardTypeId(DEFAULT_GIFTCARD_TYPE_ID);
        return giftcard;
    }

    @Before
    public void initTest() {
        giftcard = createEntity(em);
    }

    @Test
    @Transactional
    public void createGiftcard() throws Exception {
        int databaseSizeBeforeCreate = giftcardRepository.findAll().size();

        // Create the Giftcard
        restGiftcardMockMvc.perform(post("/api/giftcards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftcard)))
            .andExpect(status().isCreated());

        // Validate the Giftcard in the database
        List<Giftcard> giftcardList = giftcardRepository.findAll();
        assertThat(giftcardList).hasSize(databaseSizeBeforeCreate + 1);
        Giftcard testGiftcard = giftcardList.get(giftcardList.size() - 1);
        assertThat(testGiftcard.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGiftcard.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testGiftcard.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testGiftcard.getGiftcardTypeId()).isEqualTo(DEFAULT_GIFTCARD_TYPE_ID);

        // Validate the Giftcard in Elasticsearch
        verify(mockGiftcardSearchRepository, times(1)).save(testGiftcard);
    }

    @Test
    @Transactional
    public void createGiftcardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = giftcardRepository.findAll().size();

        // Create the Giftcard with an existing ID
        giftcard.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGiftcardMockMvc.perform(post("/api/giftcards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftcard)))
            .andExpect(status().isBadRequest());

        // Validate the Giftcard in the database
        List<Giftcard> giftcardList = giftcardRepository.findAll();
        assertThat(giftcardList).hasSize(databaseSizeBeforeCreate);

        // Validate the Giftcard in Elasticsearch
        verify(mockGiftcardSearchRepository, times(0)).save(giftcard);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftcardRepository.findAll().size();
        // set the field null
        giftcard.setName(null);

        // Create the Giftcard, which fails.

        restGiftcardMockMvc.perform(post("/api/giftcards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftcard)))
            .andExpect(status().isBadRequest());

        List<Giftcard> giftcardList = giftcardRepository.findAll();
        assertThat(giftcardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftcardRepository.findAll().size();
        // set the field null
        giftcard.setAmount(null);

        // Create the Giftcard, which fails.

        restGiftcardMockMvc.perform(post("/api/giftcards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftcard)))
            .andExpect(status().isBadRequest());

        List<Giftcard> giftcardList = giftcardRepository.findAll();
        assertThat(giftcardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftcardRepository.findAll().size();
        // set the field null
        giftcard.setUnit(null);

        // Create the Giftcard, which fails.

        restGiftcardMockMvc.perform(post("/api/giftcards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftcard)))
            .andExpect(status().isBadRequest());

        List<Giftcard> giftcardList = giftcardRepository.findAll();
        assertThat(giftcardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGiftcardTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftcardRepository.findAll().size();
        // set the field null
        giftcard.setGiftcardTypeId(null);

        // Create the Giftcard, which fails.

        restGiftcardMockMvc.perform(post("/api/giftcards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftcard)))
            .andExpect(status().isBadRequest());

        List<Giftcard> giftcardList = giftcardRepository.findAll();
        assertThat(giftcardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGiftcards() throws Exception {
        // Initialize the database
        giftcardRepository.saveAndFlush(giftcard);

        // Get all the giftcardList
        restGiftcardMockMvc.perform(get("/api/giftcards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(giftcard.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].giftcardTypeId").value(hasItem(DEFAULT_GIFTCARD_TYPE_ID)));
    }
    
    @Test
    @Transactional
    public void getGiftcard() throws Exception {
        // Initialize the database
        giftcardRepository.saveAndFlush(giftcard);

        // Get the giftcard
        restGiftcardMockMvc.perform(get("/api/giftcards/{id}", giftcard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(giftcard.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.giftcardTypeId").value(DEFAULT_GIFTCARD_TYPE_ID));
    }

    @Test
    @Transactional
    public void getNonExistingGiftcard() throws Exception {
        // Get the giftcard
        restGiftcardMockMvc.perform(get("/api/giftcards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGiftcard() throws Exception {
        // Initialize the database
        giftcardRepository.saveAndFlush(giftcard);

        int databaseSizeBeforeUpdate = giftcardRepository.findAll().size();

        // Update the giftcard
        Giftcard updatedGiftcard = giftcardRepository.findById(giftcard.getId()).get();
        // Disconnect from session so that the updates on updatedGiftcard are not directly saved in db
        em.detach(updatedGiftcard);
        updatedGiftcard
            .name(UPDATED_NAME)
            .amount(UPDATED_AMOUNT)
            .unit(UPDATED_UNIT)
            .giftcardTypeId(UPDATED_GIFTCARD_TYPE_ID);

        restGiftcardMockMvc.perform(put("/api/giftcards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGiftcard)))
            .andExpect(status().isOk());

        // Validate the Giftcard in the database
        List<Giftcard> giftcardList = giftcardRepository.findAll();
        assertThat(giftcardList).hasSize(databaseSizeBeforeUpdate);
        Giftcard testGiftcard = giftcardList.get(giftcardList.size() - 1);
        assertThat(testGiftcard.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGiftcard.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testGiftcard.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testGiftcard.getGiftcardTypeId()).isEqualTo(UPDATED_GIFTCARD_TYPE_ID);

        // Validate the Giftcard in Elasticsearch
        verify(mockGiftcardSearchRepository, times(1)).save(testGiftcard);
    }

    @Test
    @Transactional
    public void updateNonExistingGiftcard() throws Exception {
        int databaseSizeBeforeUpdate = giftcardRepository.findAll().size();

        // Create the Giftcard

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiftcardMockMvc.perform(put("/api/giftcards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftcard)))
            .andExpect(status().isBadRequest());

        // Validate the Giftcard in the database
        List<Giftcard> giftcardList = giftcardRepository.findAll();
        assertThat(giftcardList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Giftcard in Elasticsearch
        verify(mockGiftcardSearchRepository, times(0)).save(giftcard);
    }

    @Test
    @Transactional
    public void deleteGiftcard() throws Exception {
        // Initialize the database
        giftcardRepository.saveAndFlush(giftcard);

        int databaseSizeBeforeDelete = giftcardRepository.findAll().size();

        // Get the giftcard
        restGiftcardMockMvc.perform(delete("/api/giftcards/{id}", giftcard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Giftcard> giftcardList = giftcardRepository.findAll();
        assertThat(giftcardList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Giftcard in Elasticsearch
        verify(mockGiftcardSearchRepository, times(1)).deleteById(giftcard.getId());
    }

    @Test
    @Transactional
    public void searchGiftcard() throws Exception {
        // Initialize the database
        giftcardRepository.saveAndFlush(giftcard);
        when(mockGiftcardSearchRepository.search(queryStringQuery("id:" + giftcard.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(giftcard), PageRequest.of(0, 1), 1));
        // Search the giftcard
        restGiftcardMockMvc.perform(get("/api/_search/giftcards?query=id:" + giftcard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(giftcard.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].giftcardTypeId").value(hasItem(DEFAULT_GIFTCARD_TYPE_ID)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Giftcard.class);
        Giftcard giftcard1 = new Giftcard();
        giftcard1.setId(1L);
        Giftcard giftcard2 = new Giftcard();
        giftcard2.setId(giftcard1.getId());
        assertThat(giftcard1).isEqualTo(giftcard2);
        giftcard2.setId(2L);
        assertThat(giftcard1).isNotEqualTo(giftcard2);
        giftcard1.setId(null);
        assertThat(giftcard1).isNotEqualTo(giftcard2);
    }
}
