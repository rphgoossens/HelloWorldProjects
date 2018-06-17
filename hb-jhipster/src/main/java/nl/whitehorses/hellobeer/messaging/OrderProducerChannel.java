package nl.whitehorses.hellobeer.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OrderProducerChannel {

    String CHANNEL = "orderProducer";

    @Output
    MessageChannel orderProducer();
}

