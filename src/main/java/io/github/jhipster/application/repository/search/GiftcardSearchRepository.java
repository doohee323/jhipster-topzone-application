package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Giftcard;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Giftcard entity.
 */
public interface GiftcardSearchRepository extends ElasticsearchRepository<Giftcard, Long> {
}
