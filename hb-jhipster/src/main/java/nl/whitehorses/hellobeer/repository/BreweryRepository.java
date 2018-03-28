package nl.whitehorses.hellobeer.repository;

import nl.whitehorses.hellobeer.domain.Brewery;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Brewery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BreweryRepository extends JpaRepository<Brewery, Long> {

}
