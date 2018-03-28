package nl.whitehorses.hellobeer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ItemStockLevel.
 */
@Entity
@Table(name = "item_stock_level")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemStockLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "stock_date", nullable = false)
    private ZonedDateTime stockDate;

    @Column(name = "quantity")
    private Long quantity;

    @ManyToOne
    private InventoryItem inventoryItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStockDate() {
        return stockDate;
    }

    public ItemStockLevel stockDate(ZonedDateTime stockDate) {
        this.stockDate = stockDate;
        return this;
    }

    public void setStockDate(ZonedDateTime stockDate) {
        this.stockDate = stockDate;
    }

    public Long getQuantity() {
        return quantity;
    }

    public ItemStockLevel quantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public ItemStockLevel inventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
        return this;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
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
        ItemStockLevel itemStockLevel = (ItemStockLevel) o;
        if (itemStockLevel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemStockLevel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemStockLevel{" +
            "id=" + getId() +
            ", stockDate='" + getStockDate() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
