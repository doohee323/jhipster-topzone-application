package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Giftcard.
 */
@Entity
@Table(name = "giftcard")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "giftcard")
public class Giftcard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "unit", nullable = false)
    private String unit;

    @NotNull
    @Column(name = "giftcard_type_id", nullable = false)
    private Integer giftcardTypeId;

    @ManyToOne
    @JsonIgnoreProperties("giftcardTypeIds")
    private GiftcardType giftcardType;

    @OneToMany(mappedBy = "giftcard")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Gift> giftcardIds = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Giftcard name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public Giftcard amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public Giftcard unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getGiftcardTypeId() {
        return giftcardTypeId;
    }

    public Giftcard giftcardTypeId(Integer giftcardTypeId) {
        this.giftcardTypeId = giftcardTypeId;
        return this;
    }

    public void setGiftcardTypeId(Integer giftcardTypeId) {
        this.giftcardTypeId = giftcardTypeId;
    }

    public GiftcardType getGiftcardType() {
        return giftcardType;
    }

    public Giftcard giftcardType(GiftcardType giftcardType) {
        this.giftcardType = giftcardType;
        return this;
    }

    public void setGiftcardType(GiftcardType giftcardType) {
        this.giftcardType = giftcardType;
    }

    public Set<Gift> getGiftcardIds() {
        return giftcardIds;
    }

    public Giftcard giftcardIds(Set<Gift> gifts) {
        this.giftcardIds = gifts;
        return this;
    }

    public Giftcard addGiftcardId(Gift gift) {
        this.giftcardIds.add(gift);
        gift.setGiftcard(this);
        return this;
    }

    public Giftcard removeGiftcardId(Gift gift) {
        this.giftcardIds.remove(gift);
        gift.setGiftcard(null);
        return this;
    }

    public void setGiftcardIds(Set<Gift> gifts) {
        this.giftcardIds = gifts;
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
        Giftcard giftcard = (Giftcard) o;
        if (giftcard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), giftcard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Giftcard{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", amount=" + getAmount() +
            ", unit='" + getUnit() + "'" +
            ", giftcardTypeId=" + getGiftcardTypeId() +
            "}";
    }
}
