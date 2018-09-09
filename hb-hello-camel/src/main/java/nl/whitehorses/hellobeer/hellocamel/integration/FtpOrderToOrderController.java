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

        from("ftp://localhost/hello-beer?username=anonymous&move=.done&moveFailed=.error")
                .log("${body}")
                .unmarshal().jacksonxml(Order.class)
                .marshal(jacksonDataFormat)
                .log("${body}")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .to("http://localhost:8080/hello-camel/1.0/order");
    }
}
