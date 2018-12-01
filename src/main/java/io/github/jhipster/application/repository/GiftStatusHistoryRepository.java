package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.GiftStatusHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GiftStatusHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GiftStatusHistoryRepository extends JpaRepository<GiftStatusHistory, Long> {

}
