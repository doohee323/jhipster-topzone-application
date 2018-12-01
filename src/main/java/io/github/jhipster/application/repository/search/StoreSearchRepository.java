package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Store;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Store entity.
 */
public interface StoreSearchRepository extends ElasticsearchRepository<Store, Long> {
}
