package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterTzApplicationApp;

import io.github.jhipster.application.domain.GiftcardType;
import io.github.jhipster.application.repository.GiftcardTypeRepository;
import io.github.jhipster.application.repository.search.GiftcardTypeSearchRepository;
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
 * Test class for the GiftcardTypeResource REST controller.
 *
 * @see GiftcardTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterTzApplicationApp.class)
public class GiftcardTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_STORE_ID = 1;
    private static final Integer UPDATED_STORE_ID = 2;

    @Autowired
    private GiftcardTypeRepository giftcardTypeRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.GiftcardTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private GiftcardTypeSearchRepository mockGiftcardTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGiftcardTypeMockMvc;

    private GiftcardType giftcardType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GiftcardTypeResource giftcardTypeResource = new GiftcardTypeResource(giftcardTypeRepository, mockGiftcardTypeSearchRepository);
        this.restGiftcardTypeMockMvc = MockMvcBuilders.standaloneSetup(giftcardTypeResource)
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
    public static GiftcardType createEntity(EntityManager em) {
        GiftcardType giftcardType = new GiftcardType()
            .name(DEFAULT_NAME)
            .storeId(DEFAULT_STORE_ID);
        return giftcardType;
    }

    @Before
    public void initTest() {
        giftcardType = createEntity(em);
    }

    @Test
    @Transactional
    public void createGiftcardType() throws Exception {
        int databaseSizeBeforeCreate = giftcardTypeRepository.findAll().size();

        // Create the GiftcardType
        restGiftcardTypeMockMvc.perform(post("/api/giftcard-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftcardType)))
            .andExpect(status().isCreated());

        // Validate the GiftcardType in the database
        List<GiftcardType> giftcardTypeList = giftcardTypeRepository.findAll();
        assertThat(giftcardTypeList).hasSize(databaseSizeBeforeCreate + 1);
        GiftcardType testGiftcardType = giftcardTypeList.get(giftcardTypeList.size() - 1);
        assertThat(testGiftcardType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGiftcardType.getStoreId()).isEqualTo(DEFAULT_STORE_ID);

        // Validate the GiftcardType in Elasticsearch
        verify(mockGiftcardTypeSearchRepository, times(1)).save(testGiftcardType);
    }

    @Test
    @Transactional
    public void createGiftcardTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = giftcardTypeRepository.findAll().size();

        // Create the GiftcardType with an existing ID
        giftcardType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGiftcardTypeMockMvc.perform(post("/api/giftcard-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftcardType)))
            .andExpect(status().isBadRequest());

        // Validate the GiftcardType in the database
        List<GiftcardType> giftcardTypeList = giftcardTypeRepository.findAll();
        assertThat(giftcardTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the GiftcardType in Elasticsearch
        verify(mockGiftcardTypeSearchRepository, times(0)).save(giftcardType);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftcardTypeRepository.findAll().size();
        // set the field null
        giftcardType.setName(null);

        // Create the GiftcardType, which fails.

        restGiftcardTypeMockMvc.perform(post("/api/giftcard-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftcardType)))
            .andExpect(status().isBadRequest());

        List<GiftcardType> giftcardTypeList = giftcardTypeRepository.findAll();
        assertThat(giftcardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStoreIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftcardTypeRepository.findAll().size();
        // set the field null
        giftcardType.setStoreId(null);

        // Create the GiftcardType, which fails.

        restGiftcardTypeMockMvc.perform(post("/api/giftcard-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftcardType)))
            .andExpect(status().isBadRequest());

        List<GiftcardType> giftcardTypeList = giftcardTypeRepository.findAll();
        assertThat(giftcardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGiftcardTypes() throws Exception {
        // Initialize the database
        giftcardTypeRepository.saveAndFlush(giftcardType);

        // Get all the giftcardTypeList
        restGiftcardTypeMockMvc.perform(get("/api/giftcard-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(giftcardType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].storeId").value(hasItem(DEFAULT_STORE_ID)));
    }
    
    @Test
    @Transactional
    public void getGiftcardType() throws Exception {
        // Initialize the database
        giftcardTypeRepository.saveAndFlush(giftcardType);

        // Get the giftcardType
        restGiftcardTypeMockMvc.perform(get("/api/giftcard-types/{id}", giftcardType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(giftcardType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.storeId").value(DEFAULT_STORE_ID));
    }

    @Test
    @Transactional
    public void getNonExistingGiftcardType() throws Exception {
        // Get the giftcardType
        restGiftcardTypeMockMvc.perform(get("/api/giftcard-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGiftcardType() throws Exception {
        // Initialize the database
        giftcardTypeRepository.saveAndFlush(giftcardType);

        int databaseSizeBeforeUpdate = giftcardTypeRepository.findAll().size();

        // Update the giftcardType
        GiftcardType updatedGiftcardType = giftcardTypeRepository.findById(giftcardType.getId()).get();
        // Disconnect from session so that the updates on updatedGiftcardType are not directly saved in db
        em.detach(updatedGiftcardType);
        updatedGiftcardType
            .name(UPDATED_NAME)
            .storeId(UPDATED_STORE_ID);

        restGiftcardTypeMockMvc.perform(put("/api/giftcard-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGiftcardType)))
            .andExpect(status().isOk());

        // Validate the GiftcardType in the database
        List<GiftcardType> giftcardTypeList = giftcardTypeRepository.findAll();
        assertThat(giftcardTypeList).hasSize(databaseSizeBeforeUpdate);
        GiftcardType testGiftcardType = giftcardTypeList.get(giftcardTypeList.size() - 1);
        assertThat(testGiftcardType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGiftcardType.getStoreId()).isEqualTo(UPDATED_STORE_ID);

        // Validate the GiftcardType in Elasticsearch
        verify(mockGiftcardTypeSearchRepository, times(1)).save(testGiftcardType);
    }

    @Test
    @Transactional
    public void updateNonExistingGiftcardType() throws Exception {
        int databaseSizeBeforeUpdate = giftcardTypeRepository.findAll().size();

        // Create the GiftcardType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiftcardTypeMockMvc.perform(put("/api/giftcard-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(giftcardType)))
            .andExpect(status().isBadRequest());

        // Validate the GiftcardType in the database
        List<GiftcardType> giftcardTypeList = giftcardTypeRepository.findAll();
        assertThat(giftcardTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GiftcardType in Elasticsearch
        verify(mockGiftcardTypeSearchRepository, times(0)).save(giftcardType);
    }

    @Test
    @Transactional
    public void deleteGiftcardType() throws Exception {
        // Initialize the database
        giftcardTypeRepository.saveAndFlush(giftcardType);

        int databaseSizeBeforeDelete = giftcardTypeRepository.findAll().size();

        // Get the giftcardType
        restGiftcardTypeMockMvc.perform(delete("/api/giftcard-types/{id}", giftcardType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GiftcardType> giftcardTypeList = giftcardTypeRepository.findAll();
        assertThat(giftcardTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GiftcardType in Elasticsearch
        verify(mockGiftcardTypeSearchRepository, times(1)).deleteById(giftcardType.getId());
    }

    @Test
    @Transactional
    public void searchGiftcardType() throws Exception {
        // Initialize the database
        giftcardTypeRepository.saveAndFlush(giftcardType);
        when(mockGiftcardTypeSearchRepository.search(queryStringQuery("id:" + giftcardType.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(giftcardType), PageRequest.of(0, 1), 1));
        // Search the giftcardType
        restGiftcardTypeMockMvc.perform(get("/api/_search/giftcard-types?query=id:" + giftcardType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(giftcardType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].storeId").value(hasItem(DEFAULT_STORE_ID)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GiftcardType.class);
        GiftcardType giftcardType1 = new GiftcardType();
        giftcardType1.setId(1L);
        GiftcardType giftcardType2 = new GiftcardType();
        giftcardType2.setId(giftcardType1.getId());
        assertThat(giftcardType1).isEqualTo(giftcardType2);
        giftcardType2.setId(2L);
        assertThat(giftcardType1).isNotEqualTo(giftcardType2);
        giftcardType1.setId(null);
        assertThat(giftcardType1).isNotEqualTo(giftcardType2);
    }
}
