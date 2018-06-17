package nl.whitehorses.hellobeer.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface OrderConsumerChannel {

    String CHANNEL = "orderConsumer";

    @Input
    SubscribableChannel orderConsumer();
}
