package nl.whitehorses.hellobeer.client.controller;

import nl.whitehorses.hellobeer.generated.client.ApiException;
import nl.whitehorses.hellobeer.generated.client.api.BeerControllerApi;
import nl.whitehorses.hellobeer.generated.client.model.Beer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping({"/beers"})
public class BeersController {
    private BeerControllerApi beerControllerApi;

    @Autowired
    public BeersController(BeerControllerApi beerControllerApi) {
        this.beerControllerApi = beerControllerApi;
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String getBeers(Model model) {
        List<Beer> beerList = null;
        try {
            beerList = beerControllerApi.getAllBeersUsingGET(null);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        if (beerList != null) {
            model.addAttribute("beers", beerList);
        }
        return "beers";
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String addToBeers(Beer beer) {
        try {
            beerControllerApi.addToBeerRepositoryUsingPOST(beer);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return "redirect:/beers";
    }

    @ModelAttribute("beerTypes")
    public List<Beer.TypeEnum> populateBeerTypes() {
        return Arrays.asList(Beer.TypeEnum.values());
    }
}