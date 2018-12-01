package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.GiftcardType;
import io.github.jhipster.application.repository.GiftcardTypeRepository;
import io.github.jhipster.application.repository.search.GiftcardTypeSearchRepository;
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
 * REST controller for managing GiftcardType.
 */
@RestController
@RequestMapping("/api")
public class GiftcardTypeResource {

    private final Logger log = LoggerFactory.getLogger(GiftcardTypeResource.class);

    private static final String ENTITY_NAME = "giftcardType";

    private final GiftcardTypeRepository giftcardTypeRepository;

    private final GiftcardTypeSearchRepository giftcardTypeSearchRepository;

    public GiftcardTypeResource(GiftcardTypeRepository giftcardTypeRepository, GiftcardTypeSearchRepository giftcardTypeSearchRepository) {
        this.giftcardTypeRepository = giftcardTypeRepository;
        this.giftcardTypeSearchRepository = giftcardTypeSearchRepository;
    }

    /**
     * POST  /giftcard-types : Create a new giftcardType.
     *
     * @param giftcardType the giftcardType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new giftcardType, or with status 400 (Bad Request) if the giftcardType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/giftcard-types")
    @Timed
    public ResponseEntity<GiftcardType> createGiftcardType(@Valid @RequestBody GiftcardType giftcardType) throws URISyntaxException {
        log.debug("REST request to save GiftcardType : {}", giftcardType);
        if (giftcardType.getId() != null) {
            throw new BadRequestAlertException("A new giftcardType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GiftcardType result = giftcardTypeRepository.save(giftcardType);
        giftcardTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/giftcard-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /giftcard-types : Updates an existing giftcardType.
     *
     * @param giftcardType the giftcardType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated giftcardType,
     * or with status 400 (Bad Request) if the giftcardType is not valid,
     * or with status 500 (Internal Server Error) if the giftcardType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/giftcard-types")
    @Timed
    public ResponseEntity<GiftcardType> updateGiftcardType(@Valid @RequestBody GiftcardType giftcardType) throws URISyntaxException {
        log.debug("REST request to update GiftcardType : {}", giftcardType);
        if (giftcardType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GiftcardType result = giftcardTypeRepository.save(giftcardType);
        giftcardTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, giftcardType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /giftcard-types : get all the giftcardTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of giftcardTypes in body
     */
    @GetMapping("/giftcard-types")
    @Timed
    public ResponseEntity<List<GiftcardType>> getAllGiftcardTypes(Pageable pageable) {
        log.debug("REST request to get a page of GiftcardTypes");
        Page<GiftcardType> page = giftcardTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/giftcard-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /giftcard-types/:id : get the "id" giftcardType.
     *
     * @param id the id of the giftcardType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the giftcardType, or with status 404 (Not Found)
     */
    @GetMapping("/giftcard-types/{id}")
    @Timed
    public ResponseEntity<GiftcardType> getGiftcardType(@PathVariable Long id) {
        log.debug("REST request to get GiftcardType : {}", id);
        Optional<GiftcardType> giftcardType = giftcardTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(giftcardType);
    }

    /**
     * DELETE  /giftcard-types/:id : delete the "id" giftcardType.
     *
     * @param id the id of the giftcardType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/giftcard-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteGiftcardType(@PathVariable Long id) {
        log.debug("REST request to delete GiftcardType : {}", id);

        giftcardTypeRepository.deleteById(id);
        giftcardTypeSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/giftcard-types?query=:query : search for the giftcardType corresponding
     * to the query.
     *
     * @param query the query of the giftcardType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/giftcard-types")
    @Timed
    public ResponseEntity<List<GiftcardType>> searchGiftcardTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GiftcardTypes for query {}", query);
        Page<GiftcardType> page = giftcardTypeSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/giftcard-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
