package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Recipient;
import io.github.jhipster.application.repository.RecipientRepository;
import io.github.jhipster.application.repository.search.RecipientSearchRepository;
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
 * REST controller for managing Recipient.
 */
@RestController
@RequestMapping("/api")
public class RecipientResource {

    private final Logger log = LoggerFactory.getLogger(RecipientResource.class);

    private static final String ENTITY_NAME = "recipient";

    private final RecipientRepository recipientRepository;

    private final RecipientSearchRepository recipientSearchRepository;

    public RecipientResource(RecipientRepository recipientRepository, RecipientSearchRepository recipientSearchRepository) {
        this.recipientRepository = recipientRepository;
        this.recipientSearchRepository = recipientSearchRepository;
    }

    /**
     * POST  /recipients : Create a new recipient.
     *
     * @param recipient the recipient to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recipient, or with status 400 (Bad Request) if the recipient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recipients")
    @Timed
    public ResponseEntity<Recipient> createRecipient(@Valid @RequestBody Recipient recipient) throws URISyntaxException {
        log.debug("REST request to save Recipient : {}", recipient);
        if (recipient.getId() != null) {
            throw new BadRequestAlertException("A new recipient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Recipient result = recipientRepository.save(recipient);
        recipientSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/recipients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recipients : Updates an existing recipient.
     *
     * @param recipient the recipient to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recipient,
     * or with status 400 (Bad Request) if the recipient is not valid,
     * or with status 500 (Internal Server Error) if the recipient couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recipients")
    @Timed
    public ResponseEntity<Recipient> updateRecipient(@Valid @RequestBody Recipient recipient) throws URISyntaxException {
        log.debug("REST request to update Recipient : {}", recipient);
        if (recipient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Recipient result = recipientRepository.save(recipient);
        recipientSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recipient.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recipients : get all the recipients.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of recipients in body
     */
    @GetMapping("/recipients")
    @Timed
    public ResponseEntity<List<Recipient>> getAllRecipients(Pageable pageable) {
        log.debug("REST request to get a page of Recipients");
        Page<Recipient> page = recipientRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/recipients");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /recipients/:id : get the "id" recipient.
     *
     * @param id the id of the recipient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recipient, or with status 404 (Not Found)
     */
    @GetMapping("/recipients/{id}")
    @Timed
    public ResponseEntity<Recipient> getRecipient(@PathVariable Long id) {
        log.debug("REST request to get Recipient : {}", id);
        Optional<Recipient> recipient = recipientRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recipient);
    }

    /**
     * DELETE  /recipients/:id : delete the "id" recipient.
     *
     * @param id the id of the recipient to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recipients/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecipient(@PathVariable Long id) {
        log.debug("REST request to delete Recipient : {}", id);

        recipientRepository.deleteById(id);
        recipientSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/recipients?query=:query : search for the recipient corresponding
     * to the query.
     *
     * @param query the query of the recipient search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/recipients")
    @Timed
    public ResponseEntity<List<Recipient>> searchRecipients(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Recipients for query {}", query);
        Page<Recipient> page = recipientSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/recipients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
