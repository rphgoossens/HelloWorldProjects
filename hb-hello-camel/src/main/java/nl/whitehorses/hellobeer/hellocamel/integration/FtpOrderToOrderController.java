package nl.whitehorses.hellobeer.hellocamel.integration;

import nl.whitehorses.hellobeer.hellocamel.domain.Order;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;


@Component
public class FtpOrderToOrderController extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
        jacksonDataFormat.setInclude("NON_NULL");
        jacksonDataFormat.setPrettyPrint(true);

        from("{{endpoint.order.ftp}}")
                .routeId("ftp-to-order")
                .log("${body}")
                .unmarshal().jacksonxml(Order.class)
                .to("direct:new-order").id("new-order");

        from("direct:new-order")
                .routeId("order-to-order-controller")
                .marshal(jacksonDataFormat)
                .log("${body}")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .to("{{endpoint.order.http}}").id("new-order-controller");
    }
}
