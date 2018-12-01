package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterTzApplicationApp;

import io.github.jhipster.application.domain.Gift;
import io.github.jhipster.application.repository.GiftRepository;
import io.github.jhipster.application.repository.search.GiftSearchRepository;
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
 * Test class for the GiftResource REST controller.
 *
 * @see GiftResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterTzApplicationApp.class)
public class GiftResourceIntTest {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final LocalDate DEFAULT_ORDERED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDERED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_FEE = 1D;
    private static final Double UPDATED_FEE = 2D;

    private static final Double DEFAULT_EXCHANGE_RATE = 1D;
    private static final Double UPDATED_EXCHANGE_RATE = 2D;

    private static final Double DEFAULT_TOTAL_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_AMOUNT = 2D;

    private static final String DEFAULT_SEND_FROM = "AAAAAAAAAA";
    private static final String UPDATED_SEND_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DEPOSITOR = "AAAAAAAAAA";
    private static final String UPDATED_DEPOSITOR = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_AT = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_AT = "BBBBBBBBBB";

    private static final Integer DEFAULT_SENDER_ID = 1;
    private static final Integer UPDATED_SENDER_ID = 2;

    private static final Integer DEFAULT_RECIPIENT_ID = 1;
    private static final Integer UPDATED_RECIPIENT_ID = 2;

    private static final Integer DEFAULT_GIFTCARD_ID = 1;
    private static final Integer UPDATED_GIFTCARD_ID = 2;

    @Autowired
    private GiftRepository giftRepository;

    /**
     * This repository is mocked in the io.github.jhipster.application.repository.search test package.
     *
     * @see io.github.jhipster.application.repository.search.GiftSearchRepositoryMockConfiguration
     */
    @Autowired
    private GiftSearchRepository mockGiftSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGiftMockMvc;

    private Gift gift;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GiftResource giftResource = new GiftResource(giftRepository, mockGiftSearchRepository);
        this.restGiftMockMvc = MockMvcBuilders.standaloneSetup(giftResource)
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
    public static Gift createEntity(EntityManager em) {
        Gift gift = new Gift()
            .amount(DEFAULT_AMOUNT)
            .orderedAt(DEFAULT_ORDERED_AT)
            .fee(DEFAULT_FEE)
            .exchangeRate(DEFAULT_EXCHANGE_RATE)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .sendFrom(DEFAULT_SEND_FROM)
            .message(DEFAULT_MESSAGE)
            .referenceNumber(DEFAULT_REFERENCE_NUMBER)
            .depositor(DEFAULT_DEPOSITOR)
            .displayAt(DEFAULT_DISPLAY_AT)
            .senderId(DEFAULT_SENDER_ID)
            .recipientId(DEFAULT_RECIPIENT_ID)
            .giftcardId(DEFAULT_GIFTCARD_ID);
        return gift;
    }

    @Before
    public void initTest() {
        gift = createEntity(em);
    }

    @Test
    @Transactional
    public void createGift() throws Exception {
        int databaseSizeBeforeCreate = giftRepository.findAll().size();

        // Create the Gift
        restGiftMockMvc.perform(post("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gift)))
            .andExpect(status().isCreated());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeCreate + 1);
        Gift testGift = giftList.get(giftList.size() - 1);
        assertThat(testGift.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testGift.getOrderedAt()).isEqualTo(DEFAULT_ORDERED_AT);
        assertThat(testGift.getFee()).isEqualTo(DEFAULT_FEE);
        assertThat(testGift.getExchangeRate()).isEqualTo(DEFAULT_EXCHANGE_RATE);
        assertThat(testGift.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testGift.getSendFrom()).isEqualTo(DEFAULT_SEND_FROM);
        assertThat(testGift.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testGift.getReferenceNumber()).isEqualTo(DEFAULT_REFERENCE_NUMBER);
        assertThat(testGift.getDepositor()).isEqualTo(DEFAULT_DEPOSITOR);
        assertThat(testGift.getDisplayAt()).isEqualTo(DEFAULT_DISPLAY_AT);
        assertThat(testGift.getSenderId()).isEqualTo(DEFAULT_SENDER_ID);
        assertThat(testGift.getRecipientId()).isEqualTo(DEFAULT_RECIPIENT_ID);
        assertThat(testGift.getGiftcardId()).isEqualTo(DEFAULT_GIFTCARD_ID);

        // Validate the Gift in Elasticsearch
        verify(mockGiftSearchRepository, times(1)).save(testGift);
    }

    @Test
    @Transactional
    public void createGiftWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = giftRepository.findAll().size();

        // Create the Gift with an existing ID
        gift.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGiftMockMvc.perform(post("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gift)))
            .andExpect(status().isBadRequest());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeCreate);

        // Validate the Gift in Elasticsearch
        verify(mockGiftSearchRepository, times(0)).save(gift);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftRepository.findAll().size();
        // set the field null
        gift.setAmount(null);

        // Create the Gift, which fails.

        restGiftMockMvc.perform(post("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gift)))
            .andExpect(status().isBadRequest());

        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftRepository.findAll().size();
        // set the field null
        gift.setOrderedAt(null);

        // Create the Gift, which fails.

        restGiftMockMvc.perform(post("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gift)))
            .andExpect(status().isBadRequest());

        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftRepository.findAll().size();
        // set the field null
        gift.setFee(null);

        // Create the Gift, which fails.

        restGiftMockMvc.perform(post("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gift)))
            .andExpect(status().isBadRequest());

        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExchangeRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftRepository.findAll().size();
        // set the field null
        gift.setExchangeRate(null);

        // Create the Gift, which fails.

        restGiftMockMvc.perform(post("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gift)))
            .andExpect(status().isBadRequest());

        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftRepository.findAll().size();
        // set the field null
        gift.setTotalAmount(null);

        // Create the Gift, which fails.

        restGiftMockMvc.perform(post("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gift)))
            .andExpect(status().isBadRequest());

        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSendFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftRepository.findAll().size();
        // set the field null
        gift.setSendFrom(null);

        // Create the Gift, which fails.

        restGiftMockMvc.perform(post("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gift)))
            .andExpect(status().isBadRequest());

        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSenderIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftRepository.findAll().size();
        // set the field null
        gift.setSenderId(null);

        // Create the Gift, which fails.

        restGiftMockMvc.perform(post("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gift)))
            .andExpect(status().isBadRequest());

        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecipientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftRepository.findAll().size();
        // set the field null
        gift.setRecipientId(null);

        // Create the Gift, which fails.

        restGiftMockMvc.perform(post("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gift)))
            .andExpect(status().isBadRequest());

        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGiftcardIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftRepository.findAll().size();
        // set the field null
        gift.setGiftcardId(null);

        // Create the Gift, which fails.

        restGiftMockMvc.perform(post("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gift)))
            .andExpect(status().isBadRequest());

        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGifts() throws Exception {
        // Initialize the database
        giftRepository.saveAndFlush(gift);

        // Get all the giftList
        restGiftMockMvc.perform(get("/api/gifts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gift.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].orderedAt").value(hasItem(DEFAULT_ORDERED_AT.toString())))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].exchangeRate").value(hasItem(DEFAULT_EXCHANGE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].sendFrom").value(hasItem(DEFAULT_SEND_FROM.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].referenceNumber").value(hasItem(DEFAULT_REFERENCE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].depositor").value(hasItem(DEFAULT_DEPOSITOR.toString())))
            .andExpect(jsonPath("$.[*].displayAt").value(hasItem(DEFAULT_DISPLAY_AT.toString())))
            .andExpect(jsonPath("$.[*].senderId").value(hasItem(DEFAULT_SENDER_ID)))
            .andExpect(jsonPath("$.[*].recipientId").value(hasItem(DEFAULT_RECIPIENT_ID)))
            .andExpect(jsonPath("$.[*].giftcardId").value(hasItem(DEFAULT_GIFTCARD_ID)));
    }
    
    @Test
    @Transactional
    public void getGift() throws Exception {
        // Initialize the database
        giftRepository.saveAndFlush(gift);

        // Get the gift
        restGiftMockMvc.perform(get("/api/gifts/{id}", gift.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gift.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.orderedAt").value(DEFAULT_ORDERED_AT.toString()))
            .andExpect(jsonPath("$.fee").value(DEFAULT_FEE.doubleValue()))
            .andExpect(jsonPath("$.exchangeRate").value(DEFAULT_EXCHANGE_RATE.doubleValue()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.sendFrom").value(DEFAULT_SEND_FROM.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.referenceNumber").value(DEFAULT_REFERENCE_NUMBER.toString()))
            .andExpect(jsonPath("$.depositor").value(DEFAULT_DEPOSITOR.toString()))
            .andExpect(jsonPath("$.displayAt").value(DEFAULT_DISPLAY_AT.toString()))
            .andExpect(jsonPath("$.senderId").value(DEFAULT_SENDER_ID))
            .andExpect(jsonPath("$.recipientId").value(DEFAULT_RECIPIENT_ID))
            .andExpect(jsonPath("$.giftcardId").value(DEFAULT_GIFTCARD_ID));
    }

    @Test
    @Transactional
    public void getNonExistingGift() throws Exception {
        // Get the gift
        restGiftMockMvc.perform(get("/api/gifts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGift() throws Exception {
        // Initialize the database
        giftRepository.saveAndFlush(gift);

        int databaseSizeBeforeUpdate = giftRepository.findAll().size();

        // Update the gift
        Gift updatedGift = giftRepository.findById(gift.getId()).get();
        // Disconnect from session so that the updates on updatedGift are not directly saved in db
        em.detach(updatedGift);
        updatedGift
            .amount(UPDATED_AMOUNT)
            .orderedAt(UPDATED_ORDERED_AT)
            .fee(UPDATED_FEE)
            .exchangeRate(UPDATED_EXCHANGE_RATE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .sendFrom(UPDATED_SEND_FROM)
            .message(UPDATED_MESSAGE)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .depositor(UPDATED_DEPOSITOR)
            .displayAt(UPDATED_DISPLAY_AT)
            .senderId(UPDATED_SENDER_ID)
            .recipientId(UPDATED_RECIPIENT_ID)
            .giftcardId(UPDATED_GIFTCARD_ID);

        restGiftMockMvc.perform(put("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGift)))
            .andExpect(status().isOk());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeUpdate);
        Gift testGift = giftList.get(giftList.size() - 1);
        assertThat(testGift.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testGift.getOrderedAt()).isEqualTo(UPDATED_ORDERED_AT);
        assertThat(testGift.getFee()).isEqualTo(UPDATED_FEE);
        assertThat(testGift.getExchangeRate()).isEqualTo(UPDATED_EXCHANGE_RATE);
        assertThat(testGift.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testGift.getSendFrom()).isEqualTo(UPDATED_SEND_FROM);
        assertThat(testGift.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testGift.getReferenceNumber()).isEqualTo(UPDATED_REFERENCE_NUMBER);
        assertThat(testGift.getDepositor()).isEqualTo(UPDATED_DEPOSITOR);
        assertThat(testGift.getDisplayAt()).isEqualTo(UPDATED_DISPLAY_AT);
        assertThat(testGift.getSenderId()).isEqualTo(UPDATED_SENDER_ID);
        assertThat(testGift.getRecipientId()).isEqualTo(UPDATED_RECIPIENT_ID);
        assertThat(testGift.getGiftcardId()).isEqualTo(UPDATED_GIFTCARD_ID);

        // Validate the Gift in Elasticsearch
        verify(mockGiftSearchRepository, times(1)).save(testGift);
    }

    @Test
    @Transactional
    public void updateNonExistingGift() throws Exception {
        int databaseSizeBeforeUpdate = giftRepository.findAll().size();

        // Create the Gift

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiftMockMvc.perform(put("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gift)))
            .andExpect(status().isBadRequest());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Gift in Elasticsearch
        verify(mockGiftSearchRepository, times(0)).save(gift);
    }

    @Test
    @Transactional
    public void deleteGift() throws Exception {
        // Initialize the database
        giftRepository.saveAndFlush(gift);

        int databaseSizeBeforeDelete = giftRepository.findAll().size();

        // Get the gift
        restGiftMockMvc.perform(delete("/api/gifts/{id}", gift.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Gift in Elasticsearch
        verify(mockGiftSearchRepository, times(1)).deleteById(gift.getId());
    }

    @Test
    @Transactional
    public void searchGift() throws Exception {
        // Initialize the database
        giftRepository.saveAndFlush(gift);
        when(mockGiftSearchRepository.search(queryStringQuery("id:" + gift.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(gift), PageRequest.of(0, 1), 1));
        // Search the gift
        restGiftMockMvc.perform(get("/api/_search/gifts?query=id:" + gift.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gift.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].orderedAt").value(hasItem(DEFAULT_ORDERED_AT.toString())))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].exchangeRate").value(hasItem(DEFAULT_EXCHANGE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].sendFrom").value(hasItem(DEFAULT_SEND_FROM)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].referenceNumber").value(hasItem(DEFAULT_REFERENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].depositor").value(hasItem(DEFAULT_DEPOSITOR)))
            .andExpect(jsonPath("$.[*].displayAt").value(hasItem(DEFAULT_DISPLAY_AT)))
            .andExpect(jsonPath("$.[*].senderId").value(hasItem(DEFAULT_SENDER_ID)))
            .andExpect(jsonPath("$.[*].recipientId").value(hasItem(DEFAULT_RECIPIENT_ID)))
            .andExpect(jsonPath("$.[*].giftcardId").value(hasItem(DEFAULT_GIFTCARD_ID)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gift.class);
        Gift gift1 = new Gift();
        gift1.setId(1L);
        Gift gift2 = new Gift();
        gift2.setId(gift1.getId());
        assertThat(gift1).isEqualTo(gift2);
        gift2.setId(2L);
        assertThat(gift1).isNotEqualTo(gift2);
        gift1.setId(null);
        assertThat(gift1).isNotEqualTo(gift2);
    }
}
