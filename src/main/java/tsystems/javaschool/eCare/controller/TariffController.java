package tsystems.javaschool.eCare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import tsystems.javaschool.eCare.model.Tariff;
import tsystems.javaschool.eCare.service.TariffService;

import java.util.List;

@Controller
public class TariffController {

    private TariffService tariffService;

    @Autowired
    public void setFilmService(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView allTariffs() {
        List<Tariff> tariffs = tariffService.allTariffs();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tariffs");
        modelAndView.addObject("tariffsList", tariffs);
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editTariff(@ModelAttribute("tariff") Tariff tariff) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        tariffService.edit(tariff);
        return modelAndView;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") int id) {
        Tariff tariff = tariffService.getById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editTariff");
        modelAndView.addObject("tariff", tariff);
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editTariff");
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addTariff(@ModelAttribute("tariff") Tariff tariff) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        tariffService.add(tariff);
        return modelAndView;
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteTariff(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        Tariff tariff = tariffService.getById(id);
        tariffService.delete(tariff);
        return modelAndView;
    }
}
