package nl.whitehorses.hellobeer.hellocamel.controller;

import nl.whitehorses.hellobeer.hellocamel.domain.Order;
import nl.whitehorses.hellobeer.hellocamel.domain.OrderItem;
import nl.whitehorses.hellobeer.hellocamel.repository.OrderRepository;
import nl.whitehorses.hellobeer.hellocamel.util.OrderBuilder;
import nl.whitehorses.hellobeer.hellocamel.util.OrderItemBuilder;
import nl.whitehorses.hellobeer.hellocamel.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepositoryMock;

    @Before
    public void setUp() {
    }

    @Test
    public void saveOrder() throws Exception {
        OrderItem orderItem1 = new OrderItemBuilder().setInventoryItemId(1L).setQuantity(100L).build();
        OrderItem orderItem2 = new OrderItemBuilder().setInventoryItemId(2L).setQuantity(50L).build();
        Order order = new OrderBuilder().setCustomerId(1L).addOrderItems(orderItem1, orderItem2).build();

        OrderItem addedItem1 = new OrderItemBuilder().setId(2L).setInventoryItemId(1L).setQuantity(100L).build();
        OrderItem addedItem2 = new OrderItemBuilder().setId(3L).setInventoryItemId(2L).setQuantity(50L).build();
        Order added = new OrderBuilder().setId(1L).setCustomerId(1L).addOrderItems(addedItem1, addedItem2).build();

        when(orderRepositoryMock.save(any(Order.class))).thenReturn(added);

        mockMvc.perform(post("/hello-camel/1.0/order")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(order)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.customerId", is(1)))
                .andExpect(jsonPath("$.orderItems[0].id", is(2)))
                .andExpect(jsonPath("$.orderItems[0].inventoryItemId", is(1)))
                .andExpect(jsonPath("$.orderItems[0].quantity", is(100)))
                .andExpect(jsonPath("$.orderItems[1].id", is(3)))
                .andExpect(jsonPath("$.orderItems[1].inventoryItemId", is(2)))
                .andExpect(jsonPath("$.orderItems[1].quantity", is(50)));

        ArgumentCaptor<Order> dtoCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepositoryMock, times(1)).save(dtoCaptor.capture());
        verifyNoMoreInteractions(orderRepositoryMock);

        Order orderArgument = dtoCaptor.getValue();
        assertNull(orderArgument.getId());
        assertThat(orderArgument.getCustomerId(), is(1L));
        assertEquals(orderArgument.getOrderItems().size(), 2);
    }

    @Test
    public void getAllOrders() throws Exception {
        OrderItem foundItem1 = new OrderItemBuilder().setId(2L).setInventoryItemId(1L).setQuantity(100L).build();
        OrderItem foundItem2 = new OrderItemBuilder().setId(3L).setInventoryItemId(2L).setQuantity(50L).build();
        Order found = new OrderBuilder().setId(1L).setCustomerId(1L).addOrderItems(foundItem1, foundItem2).build();

        when(orderRepositoryMock.findAll()).thenReturn(Collections.singletonList(found));

        mockMvc.perform(get("/hello-camel/1.0/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].customerId", is(1)))
                .andExpect(jsonPath("$[0].orderItems[0].id", is(2)))
                .andExpect(jsonPath("$[0].orderItems[0].inventoryItemId", is(1)))
                .andExpect(jsonPath("$[0].orderItems[0].quantity", is(100)))
                .andExpect(jsonPath("$[0].orderItems[1].id", is(3)))
                .andExpect(jsonPath("$[0].orderItems[1].inventoryItemId", is(2)))
                .andExpect(jsonPath("$[0].orderItems[1].quantity", is(50)));

        verify(orderRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(orderRepositoryMock);
    }

}
