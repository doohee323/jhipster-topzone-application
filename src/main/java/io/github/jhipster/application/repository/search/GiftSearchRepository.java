package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Gift;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Gift entity.
 */
public interface GiftSearchRepository extends ElasticsearchRepository<Gift, Long> {
}
