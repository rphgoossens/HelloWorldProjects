package nl.whitehorses.hellobeer.hellocamel.integration;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class FtpOrderToOrderController extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("ftp://localhost?username=anonymous")
                .to("file:data/outbox");
    }
}
