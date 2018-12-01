package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Gift;
import io.github.jhipster.application.repository.GiftRepository;
import io.github.jhipster.application.repository.search.GiftSearchRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Gift.
 */
@RestController
@RequestMapping("/api")
public class GiftResource {

    private final Logger log = LoggerFactory.getLogger(GiftResource.class);

    private static final String ENTITY_NAME = "gift";

    private final GiftRepository giftRepository;

    private final GiftSearchRepository giftSearchRepository;

    public GiftResource(GiftRepository giftRepository, GiftSearchRepository giftSearchRepository) {
        this.giftRepository = giftRepository;
        this.giftSearchRepository = giftSearchRepository;
    }

    /**
     * POST  /gifts : Create a new gift.
     *
     * @param gift the gift to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gift, or with status 400 (Bad Request) if the gift has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gifts")
    @Timed
    public ResponseEntity<Gift> createGift(@Valid @RequestBody Gift gift) throws URISyntaxException {
        log.debug("REST request to save Gift : {}", gift);
        if (gift.getId() != null) {
            throw new BadRequestAlertException("A new gift cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gift result = giftRepository.save(gift);
        giftSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/gifts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gifts : Updates an existing gift.
     *
     * @param gift the gift to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gift,
     * or with status 400 (Bad Request) if the gift is not valid,
     * or with status 500 (Internal Server Error) if the gift couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gifts")
    @Timed
    public ResponseEntity<Gift> updateGift(@Valid @RequestBody Gift gift) throws URISyntaxException {
        log.debug("REST request to update Gift : {}", gift);
        if (gift.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Gift result = giftRepository.save(gift);
        giftSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gift.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gifts : get all the gifts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of gifts in body
     */
    @GetMapping("/gifts")
    @Timed
    public ResponseEntity<List<Gift>> getAllGifts(Pageable pageable) {
        log.debug("REST request to get a page of Gifts");
        Page<Gift> page = giftRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/gifts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /gifts/:id : get the "id" gift.
     *
     * @param id the id of the gift to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gift, or with status 404 (Not Found)
     */
    @GetMapping("/gifts/{id}")
    @Timed
    public ResponseEntity<Gift> getGift(@PathVariable Long id) {
        log.debug("REST request to get Gift : {}", id);
        Optional<Gift> gift = giftRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gift);
    }

    /**
     * DELETE  /gifts/:id : delete the "id" gift.
     *
     * @param id the id of the gift to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gifts/{id}")
    @Timed
    public ResponseEntity<Void> deleteGift(@PathVariable Long id) {
        log.debug("REST request to delete Gift : {}", id);

        giftRepository.deleteById(id);
        giftSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/gifts?query=:query : search for the gift corresponding
     * to the query.
     *
     * @param query the query of the gift search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/gifts")
    @Timed
    public ResponseEntity<List<Gift>> searchGifts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Gifts for query {}", query);
        Page<Gift> page = giftSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/gifts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
