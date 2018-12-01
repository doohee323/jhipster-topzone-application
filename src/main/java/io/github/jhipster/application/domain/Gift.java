package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Gift.
 */
@Entity
@Table(name = "gift")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "gift")
public class Gift implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "ordered_at", nullable = false)
    private LocalDate orderedAt;

    @NotNull
    @Column(name = "fee", nullable = false)
    private Double fee;

    @NotNull
    @Column(name = "exchange_rate", nullable = false)
    private Double exchangeRate;

    @NotNull
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @NotNull
    @Column(name = "send_from", nullable = false)
    private String sendFrom;

    @Column(name = "message")
    private String message;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "depositor")
    private String depositor;

    @Column(name = "display_at")
    private String displayAt;

    @NotNull
    @Column(name = "sender_id", nullable = false)
    private Integer senderId;

    @NotNull
    @Column(name = "recipient_id", nullable = false)
    private Integer recipientId;

    @NotNull
    @Column(name = "giftcard_id", nullable = false)
    private Integer giftcardId;

    @ManyToOne
    @JsonIgnoreProperties("giftcardIds")
    private Giftcard giftcard;

    @ManyToOne
    @JsonIgnoreProperties("recipientIds")
    private Recipient recipient;

    @OneToMany(mappedBy = "gift")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GiftStatusHistory> giftIds = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public Gift amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getOrderedAt() {
        return orderedAt;
    }

    public Gift orderedAt(LocalDate orderedAt) {
        this.orderedAt = orderedAt;
        return this;
    }

    public void setOrderedAt(LocalDate orderedAt) {
        this.orderedAt = orderedAt;
    }

    public Double getFee() {
        return fee;
    }

    public Gift fee(Double fee) {
        this.fee = fee;
        return this;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public Gift exchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
        return this;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public Gift totalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSendFrom() {
        return sendFrom;
    }

    public Gift sendFrom(String sendFrom) {
        this.sendFrom = sendFrom;
        return this;
    }

    public void setSendFrom(String sendFrom) {
        this.sendFrom = sendFrom;
    }

    public String getMessage() {
        return message;
    }

    public Gift message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public Gift referenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getDepositor() {
        return depositor;
    }

    public Gift depositor(String depositor) {
        this.depositor = depositor;
        return this;
    }

    public void setDepositor(String depositor) {
        this.depositor = depositor;
    }

    public String getDisplayAt() {
        return displayAt;
    }

    public Gift displayAt(String displayAt) {
        this.displayAt = displayAt;
        return this;
    }

    public void setDisplayAt(String displayAt) {
        this.displayAt = displayAt;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public Gift senderId(Integer senderId) {
        this.senderId = senderId;
        return this;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getRecipientId() {
        return recipientId;
    }

    public Gift recipientId(Integer recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
    }

    public Integer getGiftcardId() {
        return giftcardId;
    }

    public Gift giftcardId(Integer giftcardId) {
        this.giftcardId = giftcardId;
        return this;
    }

    public void setGiftcardId(Integer giftcardId) {
        this.giftcardId = giftcardId;
    }

    public Giftcard getGiftcard() {
        return giftcard;
    }

    public Gift giftcard(Giftcard giftcard) {
        this.giftcard = giftcard;
        return this;
    }

    public void setGiftcard(Giftcard giftcard) {
        this.giftcard = giftcard;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public Gift recipient(Recipient recipient) {
        this.recipient = recipient;
        return this;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Set<GiftStatusHistory> getGiftIds() {
        return giftIds;
    }

    public Gift giftIds(Set<GiftStatusHistory> giftStatusHistories) {
        this.giftIds = giftStatusHistories;
        return this;
    }

    public Gift addGiftId(GiftStatusHistory giftStatusHistory) {
        this.giftIds.add(giftStatusHistory);
        giftStatusHistory.setGift(this);
        return this;
    }

    public Gift removeGiftId(GiftStatusHistory giftStatusHistory) {
        this.giftIds.remove(giftStatusHistory);
        giftStatusHistory.setGift(null);
        return this;
    }

    public void setGiftIds(Set<GiftStatusHistory> giftStatusHistories) {
        this.giftIds = giftStatusHistories;
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
        Gift gift = (Gift) o;
        if (gift.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gift.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Gift{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", orderedAt='" + getOrderedAt() + "'" +
            ", fee=" + getFee() +
            ", exchangeRate=" + getExchangeRate() +
            ", totalAmount=" + getTotalAmount() +
            ", sendFrom='" + getSendFrom() + "'" +
            ", message='" + getMessage() + "'" +
            ", referenceNumber='" + getReferenceNumber() + "'" +
            ", depositor='" + getDepositor() + "'" +
            ", displayAt='" + getDisplayAt() + "'" +
            ", senderId=" + getSenderId() +
            ", recipientId=" + getRecipientId() +
            ", giftcardId=" + getGiftcardId() +
            "}";
    }
}
