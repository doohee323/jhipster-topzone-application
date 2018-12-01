package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterTzApplicationApp;

import io.github.jhipster.application.domain.Recipient;
import io.github.jhipster.application.repository.RecipientRepository;
import io.github.jhipster.application.repository.search.RecipientSearchRepository;
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
 * Test class for the RecipientResource REST controller.
 *
 * @see RecipientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterTzApplicationApp.class)
public class RecipientResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    @Autowired
    private RecipientRepository recipientRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.RecipientSearchRepositoryMockConfiguration
     */
    @Autowired
    private RecipientSearchRepository mockRecipientSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRecipientMockMvc;

    private Recipient recipient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecipientResource recipientResource = new RecipientResource(recipientRepository, mockRecipientSearchRepository);
        this.restRecipientMockMvc = MockMvcBuilders.standaloneSetup(recipientResource)
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
    public static Recipient createEntity(EntityManager em) {
        Recipient recipient = new Recipient()
            .name(DEFAULT_NAME)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .email(DEFAULT_EMAIL)
            .createdAt(DEFAULT_CREATED_AT)
            .userId(DEFAULT_USER_ID);
        return recipient;
    }

    @Before
    public void initTest() {
        recipient = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipient() throws Exception {
        int databaseSizeBeforeCreate = recipientRepository.findAll().size();

        // Create the Recipient
        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipient)))
            .andExpect(status().isCreated());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeCreate + 1);
        Recipient testRecipient = recipientList.get(recipientList.size() - 1);
        assertThat(testRecipient.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRecipient.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testRecipient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testRecipient.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testRecipient.getUserId()).isEqualTo(DEFAULT_USER_ID);

        // Validate the Recipient in Elasticsearch
        verify(mockRecipientSearchRepository, times(1)).save(testRecipient);
    }

    @Test
    @Transactional
    public void createRecipientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipientRepository.findAll().size();

        // Create the Recipient with an existing ID
        recipient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipient)))
            .andExpect(status().isBadRequest());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeCreate);

        // Validate the Recipient in Elasticsearch
        verify(mockRecipientSearchRepository, times(0)).save(recipient);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipientRepository.findAll().size();
        // set the field null
        recipient.setName(null);

        // Create the Recipient, which fails.

        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipient)))
            .andExpect(status().isBadRequest());

        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipientRepository.findAll().size();
        // set the field null
        recipient.setCreatedAt(null);

        // Create the Recipient, which fails.

        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipient)))
            .andExpect(status().isBadRequest());

        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipientRepository.findAll().size();
        // set the field null
        recipient.setUserId(null);

        // Create the Recipient, which fails.

        restRecipientMockMvc.perform(post("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipient)))
            .andExpect(status().isBadRequest());

        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecipients() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        // Get all the recipientList
        restRecipientMockMvc.perform(get("/api/recipients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipient.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)));
    }
    
    @Test
    @Transactional
    public void getRecipient() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        // Get the recipient
        restRecipientMockMvc.perform(get("/api/recipients/{id}", recipient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recipient.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID));
    }

    @Test
    @Transactional
    public void getNonExistingRecipient() throws Exception {
        // Get the recipient
        restRecipientMockMvc.perform(get("/api/recipients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipient() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        int databaseSizeBeforeUpdate = recipientRepository.findAll().size();

        // Update the recipient
        Recipient updatedRecipient = recipientRepository.findById(recipient.getId()).get();
        // Disconnect from session so that the updates on updatedRecipient are not directly saved in db
        em.detach(updatedRecipient);
        updatedRecipient
            .name(UPDATED_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .createdAt(UPDATED_CREATED_AT)
            .userId(UPDATED_USER_ID);

        restRecipientMockMvc.perform(put("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecipient)))
            .andExpect(status().isOk());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeUpdate);
        Recipient testRecipient = recipientList.get(recipientList.size() - 1);
        assertThat(testRecipient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRecipient.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testRecipient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testRecipient.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRecipient.getUserId()).isEqualTo(UPDATED_USER_ID);

        // Validate the Recipient in Elasticsearch
        verify(mockRecipientSearchRepository, times(1)).save(testRecipient);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipient() throws Exception {
        int databaseSizeBeforeUpdate = recipientRepository.findAll().size();

        // Create the Recipient

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipientMockMvc.perform(put("/api/recipients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipient)))
            .andExpect(status().isBadRequest());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Recipient in Elasticsearch
        verify(mockRecipientSearchRepository, times(0)).save(recipient);
    }

    @Test
    @Transactional
    public void deleteRecipient() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        int databaseSizeBeforeDelete = recipientRepository.findAll().size();

        // Get the recipient
        restRecipientMockMvc.perform(delete("/api/recipients/{id}", recipient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Recipient in Elasticsearch
        verify(mockRecipientSearchRepository, times(1)).deleteById(recipient.getId());
    }

    @Test
    @Transactional
    public void searchRecipient() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);
        when(mockRecipientSearchRepository.search(queryStringQuery("id:" + recipient.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(recipient), PageRequest.of(0, 1), 1));
        // Search the recipient
        restRecipientMockMvc.perform(get("/api/_search/recipients?query=id:" + recipient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipient.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recipient.class);
        Recipient recipient1 = new Recipient();
        recipient1.setId(1L);
        Recipient recipient2 = new Recipient();
        recipient2.setId(recipient1.getId());
        assertThat(recipient1).isEqualTo(recipient2);
        recipient2.setId(2L);
        assertThat(recipient1).isNotEqualTo(recipient2);
        recipient1.setId(null);
        assertThat(recipient1).isNotEqualTo(recipient2);
    }
}
