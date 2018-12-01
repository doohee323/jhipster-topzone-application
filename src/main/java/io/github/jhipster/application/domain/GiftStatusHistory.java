package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A GiftStatusHistory.
 */
@Entity
@Table(name = "gift_status_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "giftstatushistory")
public class GiftStatusHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "gift_status", nullable = false)
    private String giftStatus;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @NotNull
    @Column(name = "gift_id", nullable = false)
    private Integer giftId;

    @NotNull
    @Column(name = "created_by_id", nullable = false)
    private Integer createdById;

    @ManyToOne
    @JsonIgnoreProperties("giftIds")
    private Gift gift;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGiftStatus() {
        return giftStatus;
    }

    public GiftStatusHistory giftStatus(String giftStatus) {
        this.giftStatus = giftStatus;
        return this;
    }

    public void setGiftStatus(String giftStatus) {
        this.giftStatus = giftStatus;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public GiftStatusHistory createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getGiftId() {
        return giftId;
    }

    public GiftStatusHistory giftId(Integer giftId) {
        this.giftId = giftId;
        return this;
    }

    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    public Integer getCreatedById() {
        return createdById;
    }

    public GiftStatusHistory createdById(Integer createdById) {
        this.createdById = createdById;
        return this;
    }

    public void setCreatedById(Integer createdById) {
        this.createdById = createdById;
    }

    public Gift getGift() {
        return gift;
    }

    public GiftStatusHistory gift(Gift gift) {
        this.gift = gift;
        return this;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GiftStatusHistory giftStatusHistory = (GiftStatusHistory) o;
        if (giftStatusHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), giftStatusHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GiftStatusHistory{" +
            "id=" + getId() +
            ", giftStatus='" + getGiftStatus() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", giftId=" + getGiftId() +
            ", createdById=" + getCreatedById() +
            "}";
    }
}
