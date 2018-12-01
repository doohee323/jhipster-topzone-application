package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterTzApplicationApp;

import io.github.jhipster.application.domain.GiftStatusHistory;
import io.github.jhipster.application.repository.GiftStatusHistoryRepository;
import io.github.jhipster.application.repository.search.GiftStatusHistorySearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the GiftStatusHistoryResource REST controller.
 *
 * @see GiftStatusHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterTzApplicationApp.class)
public class GiftStatusHistoryResourceIntTest {

    private static final String DEFAULT_GIFT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_GIFT_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_GIFT_ID = 1;
    private static final Integer UPDATED_GIFT_ID = 2;

    private static final Integer DEFAULT_CREATED_BY_ID = 1;
    private static final Integer UPDATED_CREATED_BY_ID = 2;

    @Autowired
    private GiftStatusHistoryRepository giftStatusHistoryRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.GiftStatusHistorySearchRepositoryMockConfiguration
     */
    @Autowired
    private GiftStatusHistorySearchRepository mockGiftStatusHistorySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGiftStatusHistoryMockMvc;

    private GiftStatusHistory giftStatusHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GiftStatusHistoryResource giftStatusHistoryResource = new GiftStatusHistoryResource(giftStatusHistoryRepository, mockGiftStatusHistorySearchRepository);
        this.restGiftStatusHistoryMockMvc = MockMvcBuilders.standaloneSetup(giftStatusHistoryResource)
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
    public static GiftStatusHistory createEntity(EntityManager em) {
        GiftStatusHistory giftStatusHistory = new GiftStatusHistory()
            .giftStatus(DEFAULT_GIFT_STATUS)
            .createdAt(DEFAULT_CREATED_AT)
            .giftId(DEFAULT_GIFT_ID)
            .createdById(DEFAULT_CREATED_BY_ID);
        return giftStatusHistory;
    }

    @Before
    public void initTest() {
        giftStatusHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createGiftStatusHistory() throws Exception {
        int databaseSizeBeforeCreate = giftStatusHistoryRepository.findAll().size();

        // Create the GiftStatusHistory
        restGiftStatusHistoryMockMvc.perform(post("/api/gift-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftStatusHistory)))
            .andExpect(status().isCreated());

        // Validate the GiftStatusHistory in the database
        List<GiftStatusHistory> giftStatusHistoryList = giftStatusHistoryRepository.findAll();
        assertThat(giftStatusHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        GiftStatusHistory testGiftStatusHistory = giftStatusHistoryList.get(giftStatusHistoryList.size() - 1);
        assertThat(testGiftStatusHistory.getGiftStatus()).isEqualTo(DEFAULT_GIFT_STATUS);
        assertThat(testGiftStatusHistory.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testGiftStatusHistory.getGiftId()).isEqualTo(DEFAULT_GIFT_ID);
        assertThat(testGiftStatusHistory.getCreatedById()).isEqualTo(DEFAULT_CREATED_BY_ID);

        // Validate the GiftStatusHistory in Elasticsearch
        verify(mockGiftStatusHistorySearchRepository, times(1)).save(testGiftStatusHistory);
    }

    @Test
    @Transactional
    public void createGiftStatusHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = giftStatusHistoryRepository.findAll().size();

        // Create the GiftStatusHistory with an existing ID
        giftStatusHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGiftStatusHistoryMockMvc.perform(post("/api/gift-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftStatusHistory)))
            .andExpect(status().isBadRequest());

        // Validate the GiftStatusHistory in the database
        List<GiftStatusHistory> giftStatusHistoryList = giftStatusHistoryRepository.findAll();
        assertThat(giftStatusHistoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the GiftStatusHistory in Elasticsearch
        verify(mockGiftStatusHistorySearchRepository, times(0)).save(giftStatusHistory);
    }

    @Test
    @Transactional
    public void checkGiftStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftStatusHistoryRepository.findAll().size();
        // set the field null
        giftStatusHistory.setGiftStatus(null);

        // Create the GiftStatusHistory, which fails.

        restGiftStatusHistoryMockMvc.perform(post("/api/gift-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftStatusHistory)))
            .andExpect(status().isBadRequest());

        List<GiftStatusHistory> giftStatusHistoryList = giftStatusHistoryRepository.findAll();
        assertThat(giftStatusHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftStatusHistoryRepository.findAll().size();
        // set the field null
        giftStatusHistory.setCreatedAt(null);

        // Create the GiftStatusHistory, which fails.

        restGiftStatusHistoryMockMvc.perform(post("/api/gift-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftStatusHistory)))
            .andExpect(status().isBadRequest());

        List<GiftStatusHistory> giftStatusHistoryList = giftStatusHistoryRepository.findAll();
        assertThat(giftStatusHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGiftIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftStatusHistoryRepository.findAll().size();
        // set the field null
        giftStatusHistory.setGiftId(null);

        // Create the GiftStatusHistory, which fails.

        restGiftStatusHistoryMockMvc.perform(post("/api/gift-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftStatusHistory)))
            .andExpect(status().isBadRequest());

        List<GiftStatusHistory> giftStatusHistoryList = giftStatusHistoryRepository.findAll();
        assertThat(giftStatusHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftStatusHistoryRepository.findAll().size();
        // set the field null
        giftStatusHistory.setCreatedById(null);

        // Create the GiftStatusHistory, which fails.

        restGiftStatusHistoryMockMvc.perform(post("/api/gift-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftStatusHistory)))
            .andExpect(status().isBadRequest());

        List<GiftStatusHistory> giftStatusHistoryList = giftStatusHistoryRepository.findAll();
        assertThat(giftStatusHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGiftStatusHistories() throws Exception {
        // Initialize the database
        giftStatusHistoryRepository.saveAndFlush(giftStatusHistory);

        // Get all the giftStatusHistoryList
        restGiftStatusHistoryMockMvc.perform(get("/api/gift-status-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(giftStatusHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].giftStatus").value(hasItem(DEFAULT_GIFT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].giftId").value(hasItem(DEFAULT_GIFT_ID)))
            .andExpect(jsonPath("$.[*].createdById").value(hasItem(DEFAULT_CREATED_BY_ID)));
    }
    
    @Test
    @Transactional
    public void getGiftStatusHistory() throws Exception {
        // Initialize the database
        giftStatusHistoryRepository.saveAndFlush(giftStatusHistory);

        // Get the giftStatusHistory
        restGiftStatusHistoryMockMvc.perform(get("/api/gift-status-histories/{id}", giftStatusHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(giftStatusHistory.getId().intValue()))
            .andExpect(jsonPath("$.giftStatus").value(DEFAULT_GIFT_STATUS.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.giftId").value(DEFAULT_GIFT_ID))
            .andExpect(jsonPath("$.createdById").value(DEFAULT_CREATED_BY_ID));
    }

    @Test
    @Transactional
    public void getNonExistingGiftStatusHistory() throws Exception {
        // Get the giftStatusHistory
        restGiftStatusHistoryMockMvc.perform(get("/api/gift-status-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGiftStatusHistory() throws Exception {
        // Initialize the database
        giftStatusHistoryRepository.saveAndFlush(giftStatusHistory);

        int databaseSizeBeforeUpdate = giftStatusHistoryRepository.findAll().size();

        // Update the giftStatusHistory
        GiftStatusHistory updatedGiftStatusHistory = giftStatusHistoryRepository.findById(giftStatusHistory.getId()).get();
        // Disconnect from session so that the updates on updatedGiftStatusHistory are not directly saved in db
        em.detach(updatedGiftStatusHistory);
        updatedGiftStatusHistory
            .giftStatus(UPDATED_GIFT_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .giftId(UPDATED_GIFT_ID)
            .createdById(UPDATED_CREATED_BY_ID);

        restGiftStatusHistoryMockMvc.perform(put("/api/gift-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGiftStatusHistory)))
            .andExpect(status().isOk());

        // Validate the GiftStatusHistory in the database
        List<GiftStatusHistory> giftStatusHistoryList = giftStatusHistoryRepository.findAll();
        assertThat(giftStatusHistoryList).hasSize(databaseSizeBeforeUpdate);
        GiftStatusHistory testGiftStatusHistory = giftStatusHistoryList.get(giftStatusHistoryList.size() - 1);
        assertThat(testGiftStatusHistory.getGiftStatus()).isEqualTo(UPDATED_GIFT_STATUS);
        assertThat(testGiftStatusHistory.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testGiftStatusHistory.getGiftId()).isEqualTo(UPDATED_GIFT_ID);
        assertThat(testGiftStatusHistory.getCreatedById()).isEqualTo(UPDATED_CREATED_BY_ID);

        // Validate the GiftStatusHistory in Elasticsearch
        verify(mockGiftStatusHistorySearchRepository, times(1)).save(testGiftStatusHistory);
    }

    @Test
    @Transactional
    public void updateNonExistingGiftStatusHistory() throws Exception {
        int databaseSizeBeforeUpdate = giftStatusHistoryRepository.findAll().size();

        // Create the GiftStatusHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiftStatusHistoryMockMvc.perform(put("/api/gift-status-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftStatusHistory)))
            .andExpect(status().isBadRequest());

        // Validate the GiftStatusHistory in the database
        List<GiftStatusHistory> giftStatusHistoryList = giftStatusHistoryRepository.findAll();
        assertThat(giftStatusHistoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GiftStatusHistory in Elasticsearch
        verify(mockGiftStatusHistorySearchRepository, times(0)).save(giftStatusHistory);
    }

    @Test
    @Transactional
    public void deleteGiftStatusHistory() throws Exception {
        // Initialize the database
        giftStatusHistoryRepository.saveAndFlush(giftStatusHistory);

        int databaseSizeBeforeDelete = giftStatusHistoryRepository.findAll().size();

        // Get the giftStatusHistory
        restGiftStatusHistoryMockMvc.perform(delete("/api/gift-status-histories/{id}", giftStatusHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GiftStatusHistory> giftStatusHistoryList = giftStatusHistoryRepository.findAll();
        assertThat(giftStatusHistoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GiftStatusHistory in Elasticsearch
        verify(mockGiftStatusHistorySearchRepository, times(1)).deleteById(giftStatusHistory.getId());
    }

    @Test
    @Transactional
    public void searchGiftStatusHistory() throws Exception {
        // Initialize the database
        giftStatusHistoryRepository.saveAndFlush(giftStatusHistory);
        when(mockGiftStatusHistorySearchRepository.search(queryStringQuery("id:" + giftStatusHistory.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(giftStatusHistory), PageRequest.of(0, 1), 1));
        // Search the giftStatusHistory
        restGiftStatusHistoryMockMvc.perform(get("/api/_search/gift-status-histories?query=id:" + giftStatusHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(giftStatusHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].giftStatus").value(hasItem(DEFAULT_GIFT_STATUS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].giftId").value(hasItem(DEFAULT_GIFT_ID)))
            .andExpect(jsonPath("$.[*].createdById").value(hasItem(DEFAULT_CREATED_BY_ID)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GiftStatusHistory.class);
        GiftStatusHistory giftStatusHistory1 = new GiftStatusHistory();
        giftStatusHistory1.setId(1L);
        GiftStatusHistory giftStatusHistory2 = new GiftStatusHistory();
        giftStatusHistory2.setId(giftStatusHistory1.getId());
        assertThat(giftStatusHistory1).isEqualTo(giftStatusHistory2);
        giftStatusHistory2.setId(2L);
        assertThat(giftStatusHistory1).isNotEqualTo(giftStatusHistory2);
        giftStatusHistory1.setId(null);
        assertThat(giftStatusHistory1).isNotEqualTo(giftStatusHistory2);
    }
}
