package nl.whitehorses.beerapp.controller;

import nl.whitehorses.beerapp.model.Beer;
import nl.whitehorses.beerapp.model.BeerType;
import nl.whitehorses.beerapp.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hello-beer/")
public class BeerController {

    @Autowired
    private BeerRepository beerRepository;

    @RequestMapping(value = "/beer", method = RequestMethod.POST, produces = "application/json")
    public Beer addToBeerRepository(@RequestBody Beer beer) {

        return beerRepository.save(beer);
    }

    @RequestMapping(value = "/beers", method = RequestMethod.GET, produces = "application/json")
    public List<Beer> getAllBeers(@RequestParam(value = "type", required = false) String beerType) {
        if (beerType == null)
            return beerRepository.findAll();
        else
            return beerRepository.findByType(BeerType.valueOf(beerType));
    }

}
