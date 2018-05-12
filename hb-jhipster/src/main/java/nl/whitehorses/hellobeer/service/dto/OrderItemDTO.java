package nl.whitehorses.hellobeer.service.dto;

import java.util.Objects;

public class OrderItemDTO {
    private Long inventoryItemId;
    private Long quantity;

    public OrderItemDTO() {
    }

    public OrderItemDTO(Long inventoryItemId, Long quantity) {
        this.inventoryItemId = inventoryItemId;
        this.quantity = quantity;
    }

    public Long getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(Long inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDTO that = (OrderItemDTO) o;
        return Objects.equals(inventoryItemId, that.inventoryItemId) &&
            Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {

        return Objects.hash(inventoryItemId, quantity);
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
            "InventoryItemId=" + inventoryItemId +
            ", quantity=" + quantity +
            '}';
    }
}
