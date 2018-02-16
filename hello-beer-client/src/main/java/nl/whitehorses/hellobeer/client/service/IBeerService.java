package nl.whitehorses.hellobeer.client.service;

import nl.whitehorses.hellobeer.generated.client.model.Beer;

import java.util.List;

public interface IBeerService {
    List<Beer> getAllBeers();

    void save(Beer beer);
}
