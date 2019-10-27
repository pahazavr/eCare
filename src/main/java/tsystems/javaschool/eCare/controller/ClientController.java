package tsystems.javaschool.eCare.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import tsystems.javaschool.eCare.model.Client;
import tsystems.javaschool.eCare.service.ClientService;
import tsystems.javaschool.eCare.service.SecurityService;
import tsystems.javaschool.eCare.util.ControllerUtil;
import tsystems.javaschool.eCare.util.PageName;
import tsystems.javaschool.eCare.util.Role;
import tsystems.javaschool.eCare.validator.ClientValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Collection;

@Controller
public class ClientController {

    private static Logger logger = Logger.getLogger(ClientController.class);

    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    private SecurityService securityService;

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

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
        clientValidator.validate(client, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
            return modelAndView;
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

        if (error != null) {
            modelAndView.addObject("error", "Name or password is incorrect.");
        }

        if (logout != null) {
            modelAndView.addObject("message", "Logged out successfully.");
        }

        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(HttpServletRequest req) {
        String role = null;
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }
        req.setAttribute("role", role);
        try {
            if (role.equals(Role.ROLE_USER.toString())) {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Client client = clientService.findClientByEmail(userDetails.getUsername());
                req.setAttribute("client", client);
                req.setAttribute("pagename", PageName.CLIENT.toString());
                req.setAttribute("successmessage", "Client " + client.getName() + " loaded from database.");
                logger.info("User(client): " + client + " login in application.");
                return "welcome";
            }
        } catch (Exception ecx) {
            return "welcome";
        }

        return "welcome";
    }

    @RequestMapping(value = "/client/profile", method = RequestMethod.POST)
    public String profile(HttpServletRequest req) {
        long clientId = Long.parseLong(req.getParameter("id"));
        ControllerUtil.setRole(req);
        Client client = clientService.getClientById(clientId);
        req.setAttribute("client", client);
        req.setAttribute("pagename", PageName.CLIENT.toString());
        logger.info("User went to profile page.");
        return "client/profile";
    }

    @RequestMapping(value = "/client/editProfile", method = RequestMethod.POST)
    public String editProfile(HttpServletRequest req) {
        long clientId = Long.parseLong(req.getParameter("id"));
        ControllerUtil.setRole(req);
        Client client = clientService.getClientById(clientId);
        req.setAttribute("client", client);
        req.setAttribute("pagename", PageName.CLIENT.toString());
        logger.info("User went to edit profile page.");
        return "client/editProfile";
    }

    @RequestMapping(value = "/client/updateProfile", method = RequestMethod.POST)
    public String updateProfile(HttpServletRequest req) {
        long clientId = Long.parseLong(req.getParameter("id"));
        ControllerUtil.setRole(req);
        Client client = clientService.getClientById(clientId);
        try {
            client.setName(req.getParameter("name"));
            client.setSurname(req.getParameter("surname"));
            client.setPassport(Integer.parseInt(req.getParameter("passport")));
            client.setAddress(req.getParameter("address"));
            client.setBirthDate(Date.valueOf(req.getParameter("birthDate")));
            clientService.edit(client);
            req.setAttribute("client", client);
            req.setAttribute("pagename", PageName.CLIENT.toString());
            logger.info("User "+ client +" update profile.");
            return "client/profile";
        } catch (Exception ecx) {
            req.setAttribute("client", client);
            req.setAttribute("pagename", PageName.EDIT_CLIENT.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "client/editClient";
        }
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        return modelAndView;
    }
}
