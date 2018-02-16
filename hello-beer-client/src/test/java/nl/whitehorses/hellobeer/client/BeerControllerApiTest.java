package nl.whitehorses.hellobeer.client;

import nl.whitehorses.hellobeer.generated.client.ApiException;
import nl.whitehorses.hellobeer.generated.client.api.BeerControllerApi;
import nl.whitehorses.hellobeer.generated.client.model.Beer;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class BeerControllerApiTest {

    private final BeerControllerApi api = new BeerControllerApi();


    public BeerControllerApiTest() {
    }


    @Test
    public void addToBeerRepositoryUsingPOSTTest()
            throws ApiException {
        api.getApiClient().setBasePath("http://virtserver.swaggerhub.com/rphgoossens/hello-beer/1.1");

        Beer beer = null;
        beer = new Beer();
        beer.setName("Grolsch Pilsner");
        beer.brewery("Grolsch");
        beer.setType(Beer.TypeEnum.PILSNER);
        Beer response = api.addToBeerRepositoryUsingPOST(beer);
        Assert.assertNotNull(response.getId());
    }


    @Test
    public void getAllBeersUsingGETTest()
            throws ApiException {
        String type = null;
        List<Beer> response = api.getAllBeersUsingGET(type);
    }
}


