package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Recipient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Recipient entity.
 */
public interface RecipientSearchRepository extends ElasticsearchRepository<Recipient, Long> {
}
