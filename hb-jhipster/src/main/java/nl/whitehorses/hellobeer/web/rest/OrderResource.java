package nl.whitehorses.hellobeer.web.rest;

import com.codahale.metrics.annotation.Timed;
import nl.whitehorses.hellobeer.messaging.OrderProducerChannel;
import nl.whitehorses.hellobeer.service.dto.OrderDTO;
import nl.whitehorses.hellobeer.web.rest.errors.InvalidOrderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Order controller
 */
@RestController
@RequestMapping("/api/order")
@EnableBinding(OrderProducerChannel.class)
public class OrderResource {

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    private static final String ENTITY_NAME = "order";

    private MessageChannel orderProducer;

    public OrderResource (final MessageChannel orderProducer) {
        this.orderProducer = orderProducer;
    }

    /**
    * POST processOrder
    */
    @PostMapping("/process-order")
    @Timed
    public ResponseEntity<OrderDTO> processOrder(@Valid @RequestBody OrderDTO order) {
        log.debug("REST request to process Order : {}", order);
        if (order.getOrderId() == null) {
            throw new InvalidOrderException("Invalid order", ENTITY_NAME, "invalidorder");
        }
        orderProducer.send(MessageBuilder.withPayload(order).build());

        return ResponseEntity.ok(order);
    }

}
