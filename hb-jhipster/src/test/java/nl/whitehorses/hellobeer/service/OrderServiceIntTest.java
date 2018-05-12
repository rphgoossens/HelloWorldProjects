package nl.whitehorses.hellobeer.service;

import nl.whitehorses.hellobeer.HelloBeerApp;
import nl.whitehorses.hellobeer.domain.InventoryItem;
import nl.whitehorses.hellobeer.domain.ItemStockLevel;
import nl.whitehorses.hellobeer.domain.enumeration.ServingType;
import nl.whitehorses.hellobeer.repository.InventoryItemRepository;
import nl.whitehorses.hellobeer.repository.ItemStockLevelRepository;
import nl.whitehorses.hellobeer.service.dto.OrderDTO;
import nl.whitehorses.hellobeer.service.dto.OrderItemDTO;
import nl.whitehorses.hellobeer.web.rest.errors.InvalidOrderException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloBeerApp.class)
@Transactional
public class OrderServiceIntTest {

    private static final LocalDateTime DEFAULT_STOCK_LDATE1 = LocalDateTime.of(2018, Month.JANUARY, 1, 12, 0);
    private static final LocalDateTime DEFAULT_STOCK_LDATE2 = LocalDateTime.of(2018, Month.APRIL, 1, 12, 0);
    private static final ZonedDateTime DEFAULT_STOCK_DATE1 = ZonedDateTime.of(DEFAULT_STOCK_LDATE1, ZoneId.systemDefault());
    private static final ZonedDateTime DEFAULT_STOCK_DATE2 = ZonedDateTime.of(DEFAULT_STOCK_LDATE2, ZoneId.systemDefault());

    private static final Long DEFAULT_QUANTITY1 = 5L;
    private static final Long DEFAULT_QUANTITY2 = 10L;

    @Autowired
    private ItemStockLevelRepository itemStockLevelRepository;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private OrderService orderService;

    private InventoryItem inventoryItem1;
    private InventoryItem inventoryItem2;

    @Before
    public void init() {
        // insert inventory item 1
        InventoryItem inventoryItem1 = new InventoryItem().serving(ServingType.BOTTLE).amount(50).itemDescription("Inventory item 1");
        this.inventoryItem1 = inventoryItemRepository.save(inventoryItem1);

        // insert inventory item 2
        InventoryItem inventoryItem2 = new InventoryItem().serving(ServingType.BOTTLE).amount(50).itemDescription("Inventory item 2");
        this.inventoryItem2 = inventoryItemRepository.save(inventoryItem2);

        // insert item stock level 1-1-2018 inventory item 1
        ItemStockLevel itemStockLevel1_1 = new ItemStockLevel().inventoryItem(inventoryItem1).stockDate(DEFAULT_STOCK_DATE1).quantity(DEFAULT_QUANTITY1);
        itemStockLevelRepository.save(itemStockLevel1_1);
        // insert item stock level 1-1-2018 inventory item 1
        ItemStockLevel itemStockLevel1_2 = new ItemStockLevel().inventoryItem(inventoryItem1).stockDate(DEFAULT_STOCK_DATE2).quantity(DEFAULT_QUANTITY2);
        itemStockLevelRepository.save(itemStockLevel1_2);
        // insert item stock level 1-4-2018 inventory item 2
        ItemStockLevel itemStockLevel2_1 = new ItemStockLevel().inventoryItem(inventoryItem2).stockDate(DEFAULT_STOCK_DATE1).quantity(DEFAULT_QUANTITY1);
        itemStockLevelRepository.save(itemStockLevel2_1);
        // insert item stock level 1-4-2018 inventory item 2
        ItemStockLevel itemStockLevel2_2 = new ItemStockLevel().inventoryItem(inventoryItem2).stockDate(DEFAULT_STOCK_DATE2).quantity(DEFAULT_QUANTITY2);
        itemStockLevelRepository.save(itemStockLevel2_2);
    }

    // purchase order bound to fail on missing quantity inventory item 1
    @Test(expected = InvalidOrderException.class)
    @Transactional
    public void assertOrderFails() {
        OrderDTO order = new OrderDTO();
        List<OrderItemDTO> orderItems = new ArrayList<>();
        orderItems.add(new OrderItemDTO(this.inventoryItem1.getId(), 20L));
        orderItems.add(new OrderItemDTO(this.inventoryItem2.getId(), 20L));

        order.setCustomerId(1L);
        order.setOrderId(1L);
        order.setOrderItems(orderItems);

        orderService.registerOrder(order);
    }

    // purchase order OK, check inventory item 1 and 2 levels
    @Test
    @Transactional
    public void assertOrderOK() {
        OrderDTO order = new OrderDTO();
        List<OrderItemDTO> orderItems = new ArrayList<>();
        orderItems.add(new OrderItemDTO(this.inventoryItem1.getId(), 8L));
        orderItems.add(new OrderItemDTO(this.inventoryItem2.getId(), 4L));

        order.setCustomerId(1L);
        order.setOrderId(1L);
        order.setOrderItems(orderItems);

        orderService.registerOrder(order);

        // assert item stock level 1, should be 10-8=2
        Optional<ItemStockLevel> itemStockLevel1 = itemStockLevelRepository.findTopByInventoryItemOrderByStockDateDesc(inventoryItem1);
        assertTrue(itemStockLevel1.isPresent());
        assertEquals(Long.valueOf(2L), itemStockLevel1.get().getQuantity());

        // assert item stock level 1, should be 10-4=6
        Optional<ItemStockLevel> itemStockLevel2 = itemStockLevelRepository.findTopByInventoryItemOrderByStockDateDesc(inventoryItem2);
        assertTrue(itemStockLevel2.isPresent());
        assertEquals(Long.valueOf(6L), itemStockLevel2.get().getQuantity());
    }

}
