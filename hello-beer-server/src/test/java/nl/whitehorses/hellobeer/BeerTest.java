package nl.whitehorses.hellobeer;

import nl.whitehorses.hellobeer.model.Beer;
import nl.whitehorses.hellobeer.model.BeerType;
import nl.whitehorses.hellobeer.util.BeerConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BeerTest {

    @Autowired
    BeerConverter beerConverter;

    @Test
    public void fromEoToDto() {
        Beer beerEO = new Beer();
        beerEO.setId(1L);
        beerEO.setBrewery("Brand");
        beerEO.setName("Brand Saison");
        beerEO.setType(BeerType.OTHER);

        nl.whitehorses.hellobeer.generated.model.Beer beerDTO = beerConverter.fromEoToDto(beerEO);

        assertEquals("OTHER", beerDTO.getType().toString());

    }

    @Test
    public void fromDtoToEo() {
        nl.whitehorses.hellobeer.generated.model.Beer beerDTO = new nl.whitehorses.hellobeer.generated.model.Beer();
        beerDTO.setId(1L);
        beerDTO.setBrewery("Brand");
        beerDTO.setName("Brand Saison");
        beerDTO.setType(nl.whitehorses.hellobeer.generated.model.Beer.TypeEnum.OTHER);

        Beer beerEO = beerConverter.fromDTOtoEO(beerDTO);

        assertEquals("OTHER", beerEO.getType().toString());
    }

}
