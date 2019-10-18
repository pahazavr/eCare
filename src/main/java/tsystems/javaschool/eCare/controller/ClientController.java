package tsystems.javaschool.eCare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import tsystems.javaschool.eCare.model.Client;
import tsystems.javaschool.eCare.service.ClientService;
import tsystems.javaschool.eCare.service.SecurityService;
import tsystems.javaschool.eCare.validator.ClientValidator;

@Controller
public class ClientController {

    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    private SecurityService securityService;

    private ClientValidator clientValidator;

    @Autowired
    public void setClientValidator(ClientValidator clientValidator) {
        this.clientValidator = clientValidator;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("client", new Client());
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registration(@ModelAttribute("client") Client client, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        System.out.println(client.toString());

        System.out.println("registration: before");
        clientValidator.validate(client, bindingResult);

        System.out.println("registration: after");

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        }

        clientService.add(client);
        securityService.autoLogin(client.getEmail(), client.getConfirmPassword());

        modelAndView.addObject(client);
        modelAndView.setViewName("redirect:/welcome");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(String error, String logout) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");

        if (error != null) {
            modelAndView.addObject("error", "Name or password is incorrect.");
        }

        if (logout != null) {
            modelAndView.addObject("message", "Logged out successfully.");
        }

        return modelAndView;
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("welcome");
        return modelAndView;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        return modelAndView;
    }
}
