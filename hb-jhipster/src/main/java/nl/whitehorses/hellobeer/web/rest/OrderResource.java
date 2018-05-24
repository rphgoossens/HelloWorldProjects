package nl.whitehorses.hellobeer.web.rest;

import com.codahale.metrics.annotation.Timed;
import nl.whitehorses.hellobeer.service.dto.OrderDTO;
import nl.whitehorses.hellobeer.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
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
public class OrderResource {

    private static final String ENTITY_NAME = "order";
    private final Logger log = LoggerFactory.getLogger(OrderResource.class);
    private MessageChannel channel;

    public OrderResource(final Processor processor) {
        this.channel = processor.output();
    }

    /**
     * POST processOrder
     */
    @PostMapping("/process-order")
    @Timed
    public ResponseEntity<OrderDTO> processOrder(@Valid @RequestBody OrderDTO order) {
        log.debug("REST request to process Order : {}", order);
        if (order.getOrderId() == null) {
            throw new BadRequestAlertException("Error processing order", ENTITY_NAME, "orderfailure");
        }
        channel.send(MessageBuilder.withPayload(order).build());

        return ResponseEntity.ok(order);
    }

}
