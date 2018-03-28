package nl.whitehorses.hellobeer.repository;

import nl.whitehorses.hellobeer.domain.ItemStockLevel;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ItemStockLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemStockLevelRepository extends JpaRepository<ItemStockLevel, Long> {

}
