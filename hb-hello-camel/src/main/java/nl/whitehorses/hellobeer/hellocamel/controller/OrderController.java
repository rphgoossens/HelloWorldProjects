package nl.whitehorses.hellobeer.hellocamel.controller;

import nl.whitehorses.hellobeer.hellocamel.domain.Order;
import nl.whitehorses.hellobeer.hellocamel.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hello-camel/1.0")
public class OrderController {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST, produces = "application/json")
    public Order saveOrder(@RequestBody Order order) {

        return orderRepository.save(order);
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET, produces = "application/json")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
