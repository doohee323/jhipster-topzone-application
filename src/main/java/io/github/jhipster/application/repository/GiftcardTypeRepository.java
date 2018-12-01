package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.GiftcardType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GiftcardType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GiftcardTypeRepository extends JpaRepository<GiftcardType, Long> {

}
