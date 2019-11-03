package tsystems.javaschool.eCare.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tsystems.javaschool.eCare.ECareException;
import tsystems.javaschool.eCare.model.Option;
import tsystems.javaschool.eCare.model.Tariff;
import tsystems.javaschool.eCare.service.OptionService;
import tsystems.javaschool.eCare.service.TariffService;
import tsystems.javaschool.eCare.util.PageName;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OptionController {

    private static Logger logger = Logger.getLogger(OptionController.class);

    OptionService optionService;
    @Autowired
    public void setOptionService(OptionService optionService) {
        this.optionService = optionService;
    }

    TariffService tariffService;
    @Autowired
    public void setTariffService(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @RequestMapping(value = "/operator/newOption", method = RequestMethod.POST)
    public String newOption(Model model, HttpServletRequest req) {
//        ControllerUtil.setRole(req);
        Long tariffId = Long.valueOf(req.getParameter("tariffId"));
        Tariff tariff = tariffService.getTariffById(tariffId);
        model.addAttribute("tariff", tariff);
        model.addAttribute("newOption", new Option());
//        model.addAttribute("pagename", PageName.NEW_OPTION.toString());
        logger.info("User (operator) went to create new option page.");
        return "operator/createOption";
    }

    @RequestMapping(value = "/operator/createOption", method = RequestMethod.POST)
    public String createOption(@ModelAttribute("newOption") Option option, Model model, HttpServletRequest req) {
//        ControllerUtil.setRole(req);
        Long tariffId = Long.valueOf(req.getParameter("tariffId"));
//        String title = Util.checkStringLength(Util.checkStringOnEmpty(req.getParameter("title")));
//        int price = Util.checkInt(req.getParameter("price"));
//        int costOfConnection = Util.checkInt(req.getParameter("costOfConnection"));
//        String[] dependentOptionsArray = req.getParameterValues("dependentOptions");
//        String[] incompatibleOptionsArray = req.getParameterValues("incompatibleOptions");
        Tariff tariff = null;
        try {
            tariff = tariffService.getTariffById(tariffId);
//            Option option = new Option(tariff, title, price, costOfConnection);
            optionService.add(option);
//            option = optionService.createDependencies(option, dependentOptionsArray, incompatibleOptionsArray);
            tariff.addOption(option);
            tariffService.edit(tariff);
            model.addAttribute("option", option);
            model.addAttribute("tariff", tariff);
//            model.addAttribute("option", option);("pagename", PageName.OPTION.toString());
            logger.info("New option " + option + " has created.");
            model.addAttribute("successmessage", "Option " + option.getTitle() + " created and saved in database.");
            return "operator/viewOption";
        } catch (ECareException ecx) {
            tariff = tariffService.getTariffById(Long.valueOf(req.getParameter("tariffId")));
            model.addAttribute("tariff", tariff);
            model.addAttribute("pagename", PageName.NEW_OPTION.toString());
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/newOption";
        }
    }

    @RequestMapping(value = "/operator/viewOption", method = RequestMethod.POST)
    public String viewOption(Model model, HttpServletRequest req) {
//        ControllerUtil.setRole(req);
        Long optionId = Long.valueOf(req.getParameter("optionId"));
        Option option = optionService.getOptionById(optionId);
        model.addAttribute("option", option);

        Long tariffId = Long.valueOf(req.getParameter("tariffId"));
        Tariff tariff = tariffService.getTariffById(tariffId);
        model.addAttribute("tariff", tariff);

//        System.out.println("Dependent Options List:");
//        for(Option op: option.getDependentOptions()) {
//            System.out.println(op.toString());
//        }
//
//        System.out.println("Incompatible Options List:");
//        for(Option op: option.getIncompatibleOptions()) {
//            System.out.println(op.toString());
//        }

//        req.setAttribute("pagename", PageName.OPTION.toString());
        logger.info("User (operator) went to view option page.");
        return "operator/viewOption";
    }

    @RequestMapping(value = "/operator/editOption", method = RequestMethod.POST)
    public String editOption(Model model, HttpServletRequest req) {
//        ControllerUtil.setRole(req);
        Long optionId = Long.valueOf(req.getParameter("optionId"));
        Option option = optionService.getOptionById(optionId);
        model.addAttribute("option", option);

        Long tariffId = Long.valueOf(req.getParameter("tariffId"));
        Tariff tariff = tariffService.getTariffById(tariffId);
        model.addAttribute("tariff", tariff);

        List<Option> optionsList = optionService.getAllOptionsForTariff(tariffId);
        model.addAttribute("optionsList", optionsList);

//        req.setAttribute("pagename", PageName.OPTION_SETTINGS.toString());
        logger.info("User went to edit option page.");
        return "operator/editOption";
    }

    @RequestMapping(value = "/operator/updateOption", method = RequestMethod.POST)
    public String updateOption(Model model, HttpServletRequest req) {
//        ControllerUtil.setRole(req);
        String[] dependentOptionsArray = req.getParameterValues("dependentOptions");
        String[] incompatibleOptionsArray = req.getParameterValues("incompatibleOptions");
        Long tariffId = Long.valueOf(req.getParameter("tariffId"));
        Long optionId = Long.valueOf(req.getParameter("optionId"));
        Option option = null;
        Tariff tariff = null;
        try {
            option = optionService.getOptionById(optionId);
            option = optionService.createDependencies(option, dependentOptionsArray, incompatibleOptionsArray);
            tariff = tariffService.getTariffById(tariffId);
            model.addAttribute("tariff", tariff);
            model.addAttribute("option", option);
//            model.addAttribute("pagename", PageName.OPTION.toString());
            model.addAttribute("successmessage", "Settings for option " + option.getTitle() + " updated.");
            logger.info("Option " + option + " has been updated.");
            return "operator/viewOption";
        } catch (ECareException ecx) {
            option = optionService.getOptionById(optionId);
            model.addAttribute("option", option);
            tariff = tariffService.getTariffById(tariffId);
            model.addAttribute("tariff", tariff);
//            req.setAttribute("pagename", PageName.OPTION_SETTINGS.toString());
//            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/editOption";
        }
    }
}
