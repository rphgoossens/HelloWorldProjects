package nl.whitehorses.hellobeer.hellocamel.util;

import nl.whitehorses.hellobeer.hellocamel.domain.Order;
import nl.whitehorses.hellobeer.hellocamel.domain.OrderItem;

import java.util.ArrayList;

public class OrderBuilder {

    private Order order;

    public OrderBuilder() {
        order = new Order();
        order.setOrderItems(new ArrayList<>());
    }

    public Order build() {
        return order;
    }

    public OrderBuilder setId(final Long id) {
        order.setId(id);
        return this;
    }

    public OrderBuilder setCustomerId(final Long customerId) {
        order.setCustomerId(customerId);
        return this;
    }

    public OrderBuilder addOrderItems(final OrderItem... orderItems) {
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        return this;
    }

}
