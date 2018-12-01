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
 * A GiftcardType.
 */
@Entity
@Table(name = "giftcard_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "giftcardtype")
public class GiftcardType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "store_id", nullable = false)
    private Integer storeId;

    @ManyToOne
    @JsonIgnoreProperties("storeIds")
    private Store store;

    @OneToMany(mappedBy = "giftcardType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Giftcard> giftcardTypeIds = new HashSet<>();
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

    public GiftcardType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public GiftcardType storeId(Integer storeId) {
        this.storeId = storeId;
        return this;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Store getStore() {
        return store;
    }

    public GiftcardType store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Set<Giftcard> getGiftcardTypeIds() {
        return giftcardTypeIds;
    }

    public GiftcardType giftcardTypeIds(Set<Giftcard> giftcards) {
        this.giftcardTypeIds = giftcards;
        return this;
    }

    public GiftcardType addGiftcardTypeId(Giftcard giftcard) {
        this.giftcardTypeIds.add(giftcard);
        giftcard.setGiftcardType(this);
        return this;
    }

    public GiftcardType removeGiftcardTypeId(Giftcard giftcard) {
        this.giftcardTypeIds.remove(giftcard);
        giftcard.setGiftcardType(null);
        return this;
    }

    public void setGiftcardTypeIds(Set<Giftcard> giftcards) {
        this.giftcardTypeIds = giftcards;
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
        GiftcardType giftcardType = (GiftcardType) o;
        if (giftcardType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), giftcardType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GiftcardType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", storeId=" + getStoreId() +
            "}";
    }
}
