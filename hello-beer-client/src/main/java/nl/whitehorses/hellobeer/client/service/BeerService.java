package nl.whitehorses.hellobeer.client.service;

import nl.whitehorses.hellobeer.generated.client.ApiException;
import nl.whitehorses.hellobeer.generated.client.api.BeerControllerApi;
import nl.whitehorses.hellobeer.generated.client.model.Beer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeerService implements IBeerService {

    private BeerControllerApi beerControllerApi;

    @Autowired
    public BeerService(BeerControllerApi beerControllerApi) {
        this.beerControllerApi = beerControllerApi;
    }

    @Override
    public List<Beer> getAllBeers() {
        List<Beer> beers;
        try {
            beers = beerControllerApi.getAllBeersUsingGET(null);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        return beers;
    }

    @Override
    public void save(Beer beer) {
        try {
            beerControllerApi.addToBeerRepositoryUsingPOST(beer);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

}
