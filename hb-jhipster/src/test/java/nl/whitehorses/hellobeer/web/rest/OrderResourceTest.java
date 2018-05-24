package nl.whitehorses.hellobeer.web.rest;

import nl.whitehorses.hellobeer.HelloBeerApp;
import nl.whitehorses.hellobeer.service.dto.OrderDTO;
import nl.whitehorses.hellobeer.service.dto.OrderItemDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the Order REST controller.
 *
 * @see OrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloBeerApp.class)
public class OrderResourceTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private Processor processor;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MessageCollector messageCollector;

    private MockMvc restMockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        OrderResource orderResource = new OrderResource(processor);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(orderResource)
            .build();
    }

    /**
     * Test processOrder
     */
    @Test
    public void testProcessOrder() throws Exception {
        OrderItemDTO orderItem1 = new OrderItemDTO(1L, 50L);
        OrderItemDTO orderItem2 = new OrderItemDTO(2L, 50L);
        OrderDTO order = new OrderDTO();
        order.setCustomerId(1L);
        order.setOrderId(1L);
        order.setOrderItems(Arrays.asList(orderItem1, orderItem2));

        restMockMvc.perform(
            post("/api/order/process-order")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isOk());

        Message<?> received = messageCollector.forChannel(processor.output()).poll();
        assertNotNull(received);
        assertEquals(received.getPayload(), order);

    }

}
