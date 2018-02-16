package nl.whitehorses.hellobeer.client.controller;

import nl.whitehorses.hellobeer.client.service.IBeerService;
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
    private IBeerService beerService;

    @Autowired
    public BeersController(IBeerService beerService) {
        this.beerService = beerService;
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public String getBeers(Model model) {
        List<Beer> beers = beerService.getAllBeers();

        if (beers != null) {
            model.addAttribute("beers", beers);
        }
        return "beers";
    }

    @RequestMapping(method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String addToBeers(Beer beer) {
        beerService.save(beer);

        return "redirect:/beers";
    }

    @ModelAttribute("beerTypes")
    public List<Beer.TypeEnum> populateBeerTypes() {
        return Arrays.asList(Beer.TypeEnum.values());
    }
}