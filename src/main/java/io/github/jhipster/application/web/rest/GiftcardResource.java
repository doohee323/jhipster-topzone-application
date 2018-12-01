package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Giftcard;
import io.github.jhipster.application.repository.GiftcardRepository;
import io.github.jhipster.application.repository.search.GiftcardSearchRepository;
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
 * REST controller for managing Giftcard.
 */
@RestController
@RequestMapping("/api")
public class GiftcardResource {

    private final Logger log = LoggerFactory.getLogger(GiftcardResource.class);

    private static final String ENTITY_NAME = "giftcard";

    private final GiftcardRepository giftcardRepository;

    private final GiftcardSearchRepository giftcardSearchRepository;

    public GiftcardResource(GiftcardRepository giftcardRepository, GiftcardSearchRepository giftcardSearchRepository) {
        this.giftcardRepository = giftcardRepository;
        this.giftcardSearchRepository = giftcardSearchRepository;
    }

    /**
     * POST  /giftcards : Create a new giftcard.
     *
     * @param giftcard the giftcard to create
     * @return the ResponseEntity with status 201 (Created) and with body the new giftcard, or with status 400 (Bad Request) if the giftcard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/giftcards")
    @Timed
    public ResponseEntity<Giftcard> createGiftcard(@Valid @RequestBody Giftcard giftcard) throws URISyntaxException {
        log.debug("REST request to save Giftcard : {}", giftcard);
        if (giftcard.getId() != null) {
            throw new BadRequestAlertException("A new giftcard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Giftcard result = giftcardRepository.save(giftcard);
        giftcardSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/giftcards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /giftcards : Updates an existing giftcard.
     *
     * @param giftcard the giftcard to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated giftcard,
     * or with status 400 (Bad Request) if the giftcard is not valid,
     * or with status 500 (Internal Server Error) if the giftcard couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/giftcards")
    @Timed
    public ResponseEntity<Giftcard> updateGiftcard(@Valid @RequestBody Giftcard giftcard) throws URISyntaxException {
        log.debug("REST request to update Giftcard : {}", giftcard);
        if (giftcard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Giftcard result = giftcardRepository.save(giftcard);
        giftcardSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, giftcard.getId().toString()))
            .body(result);
    }

    /**
     * GET  /giftcards : get all the giftcards.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of giftcards in body
     */
    @GetMapping("/giftcards")
    @Timed
    public ResponseEntity<List<Giftcard>> getAllGiftcards(Pageable pageable) {
        log.debug("REST request to get a page of Giftcards");
        Page<Giftcard> page = giftcardRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/giftcards");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /giftcards/:id : get the "id" giftcard.
     *
     * @param id the id of the giftcard to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the giftcard, or with status 404 (Not Found)
     */
    @GetMapping("/giftcards/{id}")
    @Timed
    public ResponseEntity<Giftcard> getGiftcard(@PathVariable Long id) {
        log.debug("REST request to get Giftcard : {}", id);
        Optional<Giftcard> giftcard = giftcardRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(giftcard);
    }

    /**
     * DELETE  /giftcards/:id : delete the "id" giftcard.
     *
     * @param id the id of the giftcard to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/giftcards/{id}")
    @Timed
    public ResponseEntity<Void> deleteGiftcard(@PathVariable Long id) {
        log.debug("REST request to delete Giftcard : {}", id);

        giftcardRepository.deleteById(id);
        giftcardSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/giftcards?query=:query : search for the giftcard corresponding
     * to the query.
     *
     * @param query the query of the giftcard search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/giftcards")
    @Timed
    public ResponseEntity<List<Giftcard>> searchGiftcards(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Giftcards for query {}", query);
        Page<Giftcard> page = giftcardSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/giftcards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
