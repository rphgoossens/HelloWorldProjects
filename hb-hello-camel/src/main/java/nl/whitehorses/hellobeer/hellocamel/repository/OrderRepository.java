package nl.whitehorses.hellobeer.hellocamel.repository;

import nl.whitehorses.hellobeer.hellocamel.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}