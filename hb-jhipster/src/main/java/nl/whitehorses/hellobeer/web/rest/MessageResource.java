package nl.whitehorses.hellobeer.web.rest;

import com.codahale.metrics.annotation.Timed;
import nl.whitehorses.hellobeer.domain.JhiMessage;
import nl.whitehorses.hellobeer.service.stream.MessageSink;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@EnableBinding(Source.class)
public class MessageResource {

    private MessageChannel channel;
    private MessageSink messageSink;

    public MessageResource(@Qualifier(Source.OUTPUT) MessageChannel channel, MessageSink messageSink) {
        this.channel = channel;
        this.messageSink = messageSink;
    }

    @PostMapping("/messages")
    @Timed
    @ResponseStatus(HttpStatus.OK)
    public void createMessage(@RequestBody JhiMessage jhiMessage) {
        channel.send(MessageBuilder.withPayload(jhiMessage).setHeader("title", jhiMessage.getTitle()).build());
    }

    @GetMapping("/messages")
    @Timed
    public List<JhiMessage> getAllMessages() {
        return messageSink.getMessages();
    }
}
