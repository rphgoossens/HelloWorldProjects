package nl.whitehorses.hellobeer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import nl.whitehorses.hellobeer.domain.enumeration.ServingType;

/**
 * A InventoryItem.
 */
@Entity
@Table(name = "inventory_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InventoryItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_description")
    private String itemDescription;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "serving", nullable = false)
    private ServingType serving;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "rating")
    private Integer rating;

    @ManyToOne
    private Beer beer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public InventoryItem itemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
        return this;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public ServingType getServing() {
        return serving;
    }

    public InventoryItem serving(ServingType serving) {
        this.serving = serving;
        return this;
    }

    public void setServing(ServingType serving) {
        this.serving = serving;
    }

    public Integer getAmount() {
        return amount;
    }

    public InventoryItem amount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getRating() {
        return rating;
    }

    public InventoryItem rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Beer getBeer() {
        return beer;
    }

    public InventoryItem beer(Beer beer) {
        this.beer = beer;
        return this;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
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
        InventoryItem inventoryItem = (InventoryItem) o;
        if (inventoryItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inventoryItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
            "id=" + getId() +
            ", itemDescription='" + getItemDescription() + "'" +
            ", serving='" + getServing() + "'" +
            ", amount=" + getAmount() +
            ", rating=" + getRating() +
            "}";
    }
}
