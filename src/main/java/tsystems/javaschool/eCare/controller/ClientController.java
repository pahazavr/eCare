package tsystems.javaschool.eCare.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import tsystems.javaschool.eCare.ECareException;
import tsystems.javaschool.eCare.model.Client;
import tsystems.javaschool.eCare.service.ClientService;
import tsystems.javaschool.eCare.service.SecurityService;
import tsystems.javaschool.eCare.util.PageName;
import tsystems.javaschool.eCare.util.Role;
import tsystems.javaschool.eCare.validator.ClientValidator;
import tsystems.javaschool.eCare.validator.RegistrationValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@SessionAttributes({"client", "role"})
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

    private RegistrationValidator registrationValidator;

    @Autowired
    public void setRegistrationValidator(RegistrationValidator registrationValidator) {
        this.registrationValidator = registrationValidator;
    }

    private ClientValidator clientValidator;

    @Autowired
    public void setClientValidator(ClientValidator clientValidator) {
        this.clientValidator = clientValidator;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("isLoginOrRegistrationPage",true);
        model.addAttribute("newClient", new Client());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("newClient") Client client, Model model, HttpSession httpSession, BindingResult bindingResult) {

        registrationValidator.validate(client, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("isLoginOrRegistrationPage",true);
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
            model.addAttribute("errormessage","Name or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("successmessage","Logged out successfully.");
        }

        model.addAttribute("isLoginOrRegistrationPage",true);
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
            assert role != null;
            if (role.equals(Role.ROLE_USER.toString())) {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Client client = clientService.findClientByEmail(userDetails.getUsername());
                httpSession.setAttribute("client", client);
                model.addAttribute("isLoginPage",false);
                logger.info("User(client): " + client + " login in application.");
                return "welcome";
            } else if(role.equals(Role.ROLE_ADMIN.toString())) {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Client operator = clientService.findClientByEmail(userDetails.getUsername());
                httpSession.setAttribute("operator", operator);
                logger.info("User(operator): " + operator + " login in application.");
                return "welcome";
            } else {
                return "login";
            }
        } catch (ECareException ecx) {
            return "login";
        }
    }

    @RequestMapping(value = "/client/profile", method = RequestMethod.POST)
    public String profile(Model model) {
        logger.info("User went to profile page.");

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.PROFILE.toString());

        return "client/profile";
    }

    @RequestMapping(value = "/client/editProfile", method = RequestMethod.POST)
    public String editProfile(Model model, @ModelAttribute Client client) {
        logger.info("User went to edit profile page.");

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.PROFILE.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.EDIT_PROFILE.toString());

        model.addAttribute("editClient", client);
        return "client/editProfile";
    }

    @RequestMapping(value = "/client/updateProfile", method = RequestMethod.POST)
    public String updateProfile(@ModelAttribute Client client, @ModelAttribute("editClient") Client editClient,
                                Model model, HttpServletRequest request, BindingResult bindingResult) {

        List<String> breadcrumb = new ArrayList<>();

        try {
            clientValidator.validate(editClient, bindingResult);

            if (bindingResult.hasErrors()) {
                return "client/editProfile";
            }

            client.setName(request.getParameter("name"));
            client.setSurname(request.getParameter("surname"));
            client.setPassport(Long.valueOf(request.getParameter("passport")));
            client.setAddress(request.getParameter("address"));
            client.setBirthDate(Date.valueOf(request.getParameter("birthDate")));
            clientService.edit(client);
            logger.info("User "+ client +" update profile.");

            breadcrumb.add(PageName.HOME.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.PROFILE.toString());

            return "client/profile";
        } catch (Exception ecx) {

            breadcrumb.add(PageName.HOME.toString());
            breadcrumb.add(PageName.PROFILE.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.EDIT_PROFILE.toString());

            model.addAttribute("editClient", editClient);
            model.addAttribute("errormessage", ecx.getMessage());
            return "client/editProfile";
        }
    }

   @RequestMapping(value = "/client/addAmountToBalance", method = RequestMethod.POST)
    public String addAmountToBalance(@ModelAttribute Client client, HttpServletRequest request, Model model) {
        try {
            int amount = Integer.parseInt(request.getParameter("amount"));
            client.addAmountToBalance(amount);
            clientService.edit(client);

            List<String> breadcrumb = new ArrayList<>();
            breadcrumb.add(PageName.HOME.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.PROFILE.toString());

            model.addAttribute("successmessage", "Amount " + amount + " added to balance of client " + client.getFullName() + ".");
            logger.info("User added amount to balance of client " + client + ".");
            return "client/profile";
        } catch (ECareException ecx) {

            List<String> breadcrumb = new ArrayList<>();
            breadcrumb.add(PageName.HOME.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.PROFILE.toString());

            model.addAttribute("errormessage", ecx.getMessage());
            return "client/profile";
        }
    }
}
