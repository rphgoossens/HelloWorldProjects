package nl.whitehorses.hellobeer.repository;


import nl.whitehorses.hellobeer.model.Beer;
import nl.whitehorses.hellobeer.model.BeerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HelloBeerRepository extends JpaRepository<Beer, Long> {

    List<Beer> findByType(BeerType beerType);
}
