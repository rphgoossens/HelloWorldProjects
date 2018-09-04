package nl.whitehorses.hellobeer.hellocamel.repository;

import nl.whitehorses.hellobeer.hellocamel.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}