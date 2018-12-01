package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.GiftStatusHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GiftStatusHistory entity.
 */
public interface GiftStatusHistorySearchRepository extends ElasticsearchRepository<GiftStatusHistory, Long> {
}
