package nl.whitehorses.hellobeer.repository;

import nl.whitehorses.hellobeer.domain.InventoryItem;
import nl.whitehorses.hellobeer.domain.ItemStockLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data JPA repository for the ItemStockLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemStockLevelRepository extends JpaRepository<ItemStockLevel, Long> {

    Optional<ItemStockLevel> findTopByInventoryItemOrderByStockDateDesc(InventoryItem inventoryItem);
}
