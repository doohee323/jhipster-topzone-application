package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Giftcard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Giftcard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GiftcardRepository extends JpaRepository<Giftcard, Long> {

}
