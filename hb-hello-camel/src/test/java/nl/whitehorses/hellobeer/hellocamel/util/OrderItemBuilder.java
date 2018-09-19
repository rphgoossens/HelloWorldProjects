package nl.whitehorses.hellobeer.hellocamel.util;

import nl.whitehorses.hellobeer.hellocamel.domain.OrderItem;

public class OrderItemBuilder {

    private OrderItem orderItem;

    public OrderItemBuilder() {
        orderItem = new OrderItem();
    }

    public OrderItem build() {
        return orderItem;
    }

    public OrderItemBuilder setId(final Long id) {
        orderItem.setId(id);
        return this;
    }

    public OrderItemBuilder setInventoryItemId(final Long inventoryItemId) {
        orderItem.setInventoryItemId(inventoryItemId);
        return this;
    }

    public OrderItemBuilder setQuantity(final Long quantity) {
        orderItem.setQuantity(quantity);
        return this;
    }
}
