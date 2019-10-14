package tsystems.javaschool.eCare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import tsystems.javaschool.eCare.service.ClientService;

@Controller
public class LoginController {
    private ClientService clientService;

    @Autowired
    public void setFilmService(ClientService clientService) {
        this.clientService = clientService;
    }

    // Загрузка всех тарифов на главной странице
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView init() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("loginPage");
        return modelAndView;
    }
}
