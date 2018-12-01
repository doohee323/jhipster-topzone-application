package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.GiftStatusHistory;
import io.github.jhipster.application.repository.GiftStatusHistoryRepository;
import io.github.jhipster.application.repository.search.GiftStatusHistorySearchRepository;
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
 * REST controller for managing GiftStatusHistory.
 */
@RestController
@RequestMapping("/api")
public class GiftStatusHistoryResource {

    private final Logger log = LoggerFactory.getLogger(GiftStatusHistoryResource.class);

    private static final String ENTITY_NAME = "giftStatusHistory";

    private final GiftStatusHistoryRepository giftStatusHistoryRepository;

    private final GiftStatusHistorySearchRepository giftStatusHistorySearchRepository;

    public GiftStatusHistoryResource(GiftStatusHistoryRepository giftStatusHistoryRepository, GiftStatusHistorySearchRepository giftStatusHistorySearchRepository) {
        this.giftStatusHistoryRepository = giftStatusHistoryRepository;
        this.giftStatusHistorySearchRepository = giftStatusHistorySearchRepository;
    }

    /**
     * POST  /gift-status-histories : Create a new giftStatusHistory.
     *
     * @param giftStatusHistory the giftStatusHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new giftStatusHistory, or with status 400 (Bad Request) if the giftStatusHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gift-status-histories")
    @Timed
    public ResponseEntity<GiftStatusHistory> createGiftStatusHistory(@Valid @RequestBody GiftStatusHistory giftStatusHistory) throws URISyntaxException {
        log.debug("REST request to save GiftStatusHistory : {}", giftStatusHistory);
        if (giftStatusHistory.getId() != null) {
            throw new BadRequestAlertException("A new giftStatusHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GiftStatusHistory result = giftStatusHistoryRepository.save(giftStatusHistory);
        giftStatusHistorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/gift-status-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gift-status-histories : Updates an existing giftStatusHistory.
     *
     * @param giftStatusHistory the giftStatusHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated giftStatusHistory,
     * or with status 400 (Bad Request) if the giftStatusHistory is not valid,
     * or with status 500 (Internal Server Error) if the giftStatusHistory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gift-status-histories")
    @Timed
    public ResponseEntity<GiftStatusHistory> updateGiftStatusHistory(@Valid @RequestBody GiftStatusHistory giftStatusHistory) throws URISyntaxException {
        log.debug("REST request to update GiftStatusHistory : {}", giftStatusHistory);
        if (giftStatusHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GiftStatusHistory result = giftStatusHistoryRepository.save(giftStatusHistory);
        giftStatusHistorySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, giftStatusHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gift-status-histories : get all the giftStatusHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of giftStatusHistories in body
     */
    @GetMapping("/gift-status-histories")
    @Timed
    public ResponseEntity<List<GiftStatusHistory>> getAllGiftStatusHistories(Pageable pageable) {
        log.debug("REST request to get a page of GiftStatusHistories");
        Page<GiftStatusHistory> page = giftStatusHistoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/gift-status-histories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /gift-status-histories/:id : get the "id" giftStatusHistory.
     *
     * @param id the id of the giftStatusHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the giftStatusHistory, or with status 404 (Not Found)
     */
    @GetMapping("/gift-status-histories/{id}")
    @Timed
    public ResponseEntity<GiftStatusHistory> getGiftStatusHistory(@PathVariable Long id) {
        log.debug("REST request to get GiftStatusHistory : {}", id);
        Optional<GiftStatusHistory> giftStatusHistory = giftStatusHistoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(giftStatusHistory);
    }

    /**
     * DELETE  /gift-status-histories/:id : delete the "id" giftStatusHistory.
     *
     * @param id the id of the giftStatusHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gift-status-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteGiftStatusHistory(@PathVariable Long id) {
        log.debug("REST request to delete GiftStatusHistory : {}", id);

        giftStatusHistoryRepository.deleteById(id);
        giftStatusHistorySearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/gift-status-histories?query=:query : search for the giftStatusHistory corresponding
     * to the query.
     *
     * @param query the query of the giftStatusHistory search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/gift-status-histories")
    @Timed
    public ResponseEntity<List<GiftStatusHistory>> searchGiftStatusHistories(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GiftStatusHistories for query {}", query);
        Page<GiftStatusHistory> page = giftStatusHistorySearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/gift-status-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
