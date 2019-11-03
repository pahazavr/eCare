package tsystems.javaschool.eCare.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import tsystems.javaschool.eCare.ECareException;
import tsystems.javaschool.eCare.model.Client;
import tsystems.javaschool.eCare.service.ClientService;
import tsystems.javaschool.eCare.service.SecurityService;
import tsystems.javaschool.eCare.util.ControllerUtil;
import tsystems.javaschool.eCare.util.PageName;
import tsystems.javaschool.eCare.util.Role;
import tsystems.javaschool.eCare.validator.ClientValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.Collection;

@Controller
@SessionAttributes("client")
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
    public String registration(Model model) {
        model.addAttribute("newClient", new Client());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("newClient") Client client, HttpSession httpSession, BindingResult bindingResult) {

        clientValidator.validate(client, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        clientService.add(client);
        securityService.autoLogin(client.getEmail(), client.getConfirmPassword());

        String role = null;
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }
        httpSession.setAttribute("role", role);
        httpSession.setAttribute("client", client);
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpSession httpSession, String error, String logout) {

        if (error != null) {
            model.addAttribute("error", "Name or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model, HttpSession httpSession) {
        String role = null;
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }
        httpSession.setAttribute("role", role);
        try {
            if (role.equals(Role.ROLE_USER.toString())) {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Client client = clientService.findClientByEmail(userDetails.getUsername());
                httpSession.setAttribute("client", client);
                logger.info("User(client): " + client + " login in application.");
                return "welcome";
            } else {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Client operator = clientService.findClientByEmail(userDetails.getUsername());
                httpSession.setAttribute("operator", operator);
                logger.info("User(operator): " + operator + " login in application.");
                return "welcome";
            }
        } catch (Exception ecx) {
            return "welcome";
        }
    }

    @RequestMapping(value = "/client/profile", method = RequestMethod.POST)
    public String profile() {
        logger.info("User went to profile page.");
        return "client/profile";
    }

    @RequestMapping(value = "/client/editProfile", method = RequestMethod.POST)
    public String editProfile(Model model, @ModelAttribute Client client) {
        logger.info("User went to edit profile page.");
        model.addAttribute("editClient", client);
        return "client/editProfile";
    }

    @RequestMapping(value = "/client/updateProfile", method = RequestMethod.POST)
    public String updateProfile(@ModelAttribute Client client, HttpServletRequest request) {
        try {
            client.setName(request.getParameter("name"));
            client.setSurname(request.getParameter("surname"));
            client.setPassport(Long.valueOf(request.getParameter("passport")));
            client.setAddress(request.getParameter("address"));
            client.setBirthDate(Date.valueOf(request.getParameter("birthDate")));
            clientService.edit(client);
            logger.info("User "+ client +" update profile.");
            return "client/profile";
        } catch (Exception ecx) {
            request.setAttribute("client", client);
            request.setAttribute("pagename", PageName.EDIT_CLIENT.toString());
            request.setAttribute("errormessage", ecx.getMessage());
            return "client/editProfile";
        }
    }

   @RequestMapping(value = "/client/addAmountToBalance", method = RequestMethod.POST)
    public String addAmountToBalance(@ModelAttribute Client client, HttpServletRequest request) {
        try {
//            int amount = Util.checkInt(req.getParameter("amount"));
            int amount = Integer.parseInt(request.getParameter("amount"));
            client.addAmountToBalance(amount);
            clientService.edit(client);
//            httpSession.setAttribute("client", client);
//            req.setAttribute("pagename", PageName.CLIENT.toString());
//            req.setAttribute("successmessage", "Amount " + amount + " added to balance of client " + client.getFullName() + ".");
            logger.info("User added amount to balance of client " + client + ".");
            return "client/profile";
        } catch (ECareException ecx) {
//            req.setAttribute("client", client);
//            req.setAttribute("pagename", PageName.CLIENT.toString());
//            req.setAttribute("errormessage", ecx.getMessage());
            return "client/profile";
        }
    }
}
