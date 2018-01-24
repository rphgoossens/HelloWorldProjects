package nl.whitehorses.hellobeer.service;

import nl.whitehorses.hellobeer.model.Beer;
import nl.whitehorses.hellobeer.model.BeerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BeerTypeTransformer {

    private static final Logger logger = LoggerFactory.getLogger(BeerTypeTransformer.class);


    public nl.whitehorses.hellobeer.generated.model.Beer fromEoToDto(Beer beerEO) {
        nl.whitehorses.hellobeer.generated.model.Beer beerDTO = new nl.whitehorses.hellobeer.generated.model.Beer();
        org.springframework.beans.BeanUtils.copyProperties(beerEO, beerDTO);
        beerDTO.setType(nl.whitehorses.hellobeer.generated.model.Beer.TypeEnum.valueOf(beerEO.getType().toString()));

        return beerDTO;
    }

    public Beer fromDTOtoEO(nl.whitehorses.hellobeer.generated.model.Beer beerDTO) {
        Beer beerEO = new Beer();
        org.springframework.beans.BeanUtils.copyProperties(beerDTO, beerEO);
        beerEO.setType(BeerType.valueOf(beerDTO.getType().toString()));

        return beerEO;
    }

}
