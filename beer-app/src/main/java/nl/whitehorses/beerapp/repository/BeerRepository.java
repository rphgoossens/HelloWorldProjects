package nl.whitehorses.beerapp.repository;

import nl.whitehorses.beerapp.model.Beer;
import nl.whitehorses.beerapp.model.BeerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeerRepository extends JpaRepository<Beer, Long> {

    List<Beer> findByType(BeerType beerType);
}
