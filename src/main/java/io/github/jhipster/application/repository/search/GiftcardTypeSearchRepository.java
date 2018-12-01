package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.GiftcardType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GiftcardType entity.
 */
public interface GiftcardTypeSearchRepository extends ElasticsearchRepository<GiftcardType, Long> {
}
