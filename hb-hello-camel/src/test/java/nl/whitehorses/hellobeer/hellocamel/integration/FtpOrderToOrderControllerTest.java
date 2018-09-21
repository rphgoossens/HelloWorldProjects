package nl.whitehorses.hellobeer.hellocamel.integration;

import nl.whitehorses.hellobeer.hellocamel.domain.Order;
import nl.whitehorses.hellobeer.hellocamel.domain.OrderItem;
import nl.whitehorses.hellobeer.hellocamel.util.OrderBuilder;
import nl.whitehorses.hellobeer.hellocamel.util.OrderItemBuilder;
import nl.whitehorses.hellobeer.hellocamel.util.TestUtil;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasNoJsonPath;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
public class FtpOrderToOrderControllerTest {

    private static boolean adviced = false;
    @Autowired
    private CamelContext camelContext;
    @EndpointInject(uri = "direct:input")
    private ProducerTemplate ftpEndpoint;
    @EndpointInject(uri = "direct:new-order")
    private ProducerTemplate orderEndpoint;
    @EndpointInject(uri = "mock:new-order")
    private MockEndpoint mockNewOrder;
    @EndpointInject(uri = "mock:new-order-controller")
    private MockEndpoint mockNewOrderController;

    @Before
    public void setUp() throws Exception {
        if (!adviced) {
            camelContext.getRouteDefinition("ftp-to-order")
                    .adviceWith(camelContext, new AdviceWithRouteBuilder() {
                        @Override
                        public void configure() {
                            replaceFromWith(ftpEndpoint.getDefaultEndpoint());
                            weaveById("new-order").replace().to(mockNewOrder.getEndpointUri());
                        }
                    });

            camelContext.getRouteDefinition("order-to-order-controller")
                    .adviceWith(camelContext, new AdviceWithRouteBuilder() {
                        @Override
                        public void configure() {
                            weaveById("new-order-controller").replace().to(mockNewOrderController.getEndpointUri());
                        }
                    });

            adviced = true;
        }
        camelContext.start();
    }

    @Test
    public void ftpToOrder() throws Exception {
        String requestPayload = TestUtil.inputStreamToString(getClass().getResourceAsStream("/data/inbox/newOrder.xml"));
        ftpEndpoint.sendBody(requestPayload);

        Order order = mockNewOrder.getExchanges().get(0).getIn().getBody(Order.class);
        assertNull(order.getId());
        assertThat(order.getCustomerId(), is(1L));
        assertNull(order.getOrderItems().get(0).getId());
        assertThat(order.getOrderItems().get(0).getInventoryItemId(), is(1L));
        assertThat(order.getOrderItems().get(0).getQuantity(), is(100L));
        assertNull(order.getOrderItems().get(1).getId());
        assertThat(order.getCustomerId(), is(1L));
        assertThat(order.getOrderItems().get(1).getInventoryItemId(), is(2L));
        assertThat(order.getOrderItems().get(1).getQuantity(), is(50L));
    }

    @Test
    public void OrderToController() {
        OrderItem orderItem1 = new OrderItemBuilder().setInventoryItemId(1L).setQuantity(100L).build();
        OrderItem orderItem2 = new OrderItemBuilder().setInventoryItemId(2L).setQuantity(50L).build();
        Order order = new OrderBuilder().setCustomerId(1L).addOrderItems(orderItem1, orderItem2).build();
        orderEndpoint.sendBody(order);

        String jsonOrder = mockNewOrderController.getExchanges().get(0).getIn().getBody(String.class);
        assertThat(jsonOrder, hasNoJsonPath("$.id"));
        assertThat(jsonOrder, hasJsonPath("$.customerId", is(1)));
        assertThat(jsonOrder, hasNoJsonPath("$.orderItems[0].id"));
        assertThat(jsonOrder, hasJsonPath("$.orderItems[0].inventoryItemId", is(1)));
        assertThat(jsonOrder, hasJsonPath("$.orderItems[0].quantity", is(100)));
        assertThat(jsonOrder, hasNoJsonPath("$.orderItems[1].id"));
        assertThat(jsonOrder, hasJsonPath("$.orderItems[1].inventoryItemId", is(2)));
        assertThat(jsonOrder, hasJsonPath("$.orderItems[1].quantity", is(50)));
        assertThat(jsonOrder, hasNoJsonPath("$.orderItems[1].id"));
    }

    @After
    public void tearDown() throws Exception {
        camelContext.stop();
    }

}