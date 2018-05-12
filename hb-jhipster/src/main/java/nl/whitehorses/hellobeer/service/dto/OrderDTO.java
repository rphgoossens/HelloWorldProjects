package nl.whitehorses.hellobeer.service.dto;

import java.util.List;
import java.util.Objects;

public class OrderDTO {
    private Long orderId;
    private Long customerId;
    private List<OrderItemDTO> orderItems;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(orderId, orderDTO.orderId) &&
            Objects.equals(customerId, orderDTO.customerId) &&
            Objects.equals(orderItems, orderDTO.orderItems);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId, customerId, orderItems);
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
            "orderId=" + orderId +
            ", customerId=" + customerId +
            ", orderItems=" + orderItems +
            '}';
    }
}
