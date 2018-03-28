package nl.whitehorses.hellobeer.repository;

import nl.whitehorses.hellobeer.domain.Beer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Beer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {

}
