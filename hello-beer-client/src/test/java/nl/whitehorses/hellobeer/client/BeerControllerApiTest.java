package nl.whitehorses.hellobeer.client;

import nl.whitehorses.hellobeer.generated.client.ApiException;
import nl.whitehorses.hellobeer.generated.client.api.BeerControllerApi;
import nl.whitehorses.hellobeer.generated.client.model.Beer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BeerControllerApiTest {

    @Autowired
    private BeerControllerApi beerControllerApi;

    @Before
    public void setUp() {
        beerControllerApi.getApiClient().setBasePath("http://virtserver.swaggerhub.com/rphgoossens/hello-beer/1.1");
    }

    @Test
    public void addToBeerRepositoryUsingPOSTTest()
            throws ApiException {
        Beer beer = null;
        beer = new Beer();
        beer.setName("Grolsch Pilsner");
        beer.brewery("Grolsch");
        beer.setType(Beer.TypeEnum.PILSNER);
        Beer response = beerControllerApi.addToBeerRepositoryUsingPOST(beer);
        Assert.assertNotNull(response.getId());
    }

    @Test
    public void getAllBeersUsingGETTest()
            throws ApiException {
        String type = null;
        List<Beer> response = beerControllerApi.getAllBeersUsingGET(type);
        Assert.assertTrue(response.size() == 1);
    }

}


