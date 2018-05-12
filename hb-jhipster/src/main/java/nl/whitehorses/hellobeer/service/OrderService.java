package nl.whitehorses.hellobeer.service;

import nl.whitehorses.hellobeer.domain.InventoryItem;
import nl.whitehorses.hellobeer.domain.ItemStockLevel;
import nl.whitehorses.hellobeer.repository.InventoryItemRepository;
import nl.whitehorses.hellobeer.repository.ItemStockLevelRepository;
import nl.whitehorses.hellobeer.service.dto.OrderDTO;
import nl.whitehorses.hellobeer.service.dto.OrderItemDTO;
import nl.whitehorses.hellobeer.web.rest.errors.InvalidOrderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private final ItemStockLevelRepository itemStockLevelRepository;
    private final InventoryItemRepository inventoryItemRepository;

    private static final String ENTITY_NAME = "order";

    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    public OrderService(ItemStockLevelRepository itemStockLevelRepository, InventoryItemRepository inventoryItemRepository) {
        this.itemStockLevelRepository = itemStockLevelRepository;
        this.inventoryItemRepository = inventoryItemRepository;
    }

    public void registerOrder(OrderDTO order) throws InvalidOrderException {
        // Map to store new item stock levels
        List<ItemStockLevel> itemStockLevelList = new ArrayList<>();

        for (OrderItemDTO orderItem : order.getOrderItems()) {
            ItemStockLevel itemStockLevelNew = processOrderItem(orderItem.getInventoryItemId(), orderItem.getQuantity());
            itemStockLevelList.add(itemStockLevelNew);
        }

        itemStockLevelRepository.save(itemStockLevelList);
        log.debug("Order processed");
    }

    // validate order items before processing
    // - assuming there are no multiple entries for one inventory item in the order
    // - if one order item entry fails, the whole order fails.
    private ItemStockLevel processOrderItem(Long inventoryItemId, Long qtyOrdered) {

        final InventoryItem inventoryItem = inventoryItemRepository.findOne(inventoryItemId);
        if (inventoryItem == null) {
            throw new InvalidOrderException("Invalid order", ENTITY_NAME, "invalidorder");
        }

        // find item stock level
        final Optional<ItemStockLevel> itemStockLevel = itemStockLevelRepository.findTopByInventoryItemOrderByStockDateDesc(inventoryItem);
        if (!itemStockLevel.isPresent()) {
            throw new InvalidOrderException("Invalid order", ENTITY_NAME, "invalidorder");
        }

        // check if quantity available
        Long qtyCurrent = itemStockLevel.get().getQuantity();
        Long newqty = qtyCurrent - qtyOrdered;
        if (newqty < 0L) {
            throw new InvalidOrderException("Invalid order", ENTITY_NAME, "invalidorder");
        }

        // construct new item stock level
        ItemStockLevel itemStockLevelNew = new ItemStockLevel();
        itemStockLevelNew.setInventoryItem(inventoryItem);
        itemStockLevelNew.setQuantity(newqty);
        itemStockLevelNew.setStockDate(ZonedDateTime.now(ZoneId.systemDefault()));
        return itemStockLevelNew;
    }

}
