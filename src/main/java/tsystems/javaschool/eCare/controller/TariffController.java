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
@RequestMapping(value = "/tariffs", method = RequestMethod.GET)
public class TariffController {

    private TariffService tariffService;

    @Autowired
    public void setFilmService(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    // Загрузка всех тарифов на главной странице
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView allTariffs() {
        List<Tariff> tariffs = tariffService.allTariffs();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/tariffs");
        modelAndView.addObject("tariffsList", tariffs);
        return modelAndView;
    }

    // Редактирование тарифа с переходом на страницу "/edit/{id}"
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") int id) {
        Tariff tariff = tariffService.getById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editTariff");
        modelAndView.addObject("tariff", tariff);
        return modelAndView;
    }

    // Добавление тафрифа с переходом на страницу "/add"
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editTariff");
        return modelAndView;
    }

    // После нажатия на кнопку delete
    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteTariff(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        Tariff tariff = tariffService.getById(id);
        tariffService.delete(tariff);
        return modelAndView;
    }

    // После нажатия кнопки Изменить на странице "/edit"
    // происходит redirect на страницу "/"
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editTariff(@ModelAttribute("tariff") Tariff tariff) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        tariffService.edit(tariff);
        return modelAndView;
    }

    // После нажатия кнопки Добавить на странице "/add"
    // происходит redirect на страницу "/"
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addTariff(@ModelAttribute("tariff") Tariff tariff) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        tariffService.add(tariff);
        return modelAndView;
    }
}
