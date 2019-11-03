package tsystems.javaschool.eCare.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import tsystems.javaschool.eCare.ECareException;
import tsystems.javaschool.eCare.model.Option;
import tsystems.javaschool.eCare.model.Tariff;
import tsystems.javaschool.eCare.service.OptionService;
import tsystems.javaschool.eCare.service.TariffService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TariffController {

    private static Logger logger = Logger.getLogger(TariffController.class);

    private TariffService tariffService;

    @Autowired
    public void setTariffService(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    private OptionService optionService;

    @Autowired
    public void setOptionService(OptionService optionService) {
        this.optionService = optionService;
    }

    @RequestMapping(value = "/operator/viewAllTariffs", method = RequestMethod.POST)
    public String viewAllTariffs(Model model, HttpServletRequest req) {
//        ControllerUtil.setRole(req);
        List<Tariff> tariffsList = tariffService.getAllTariffs();
        model.addAttribute("tariffsList", tariffsList);
//        req.setAttribute("pagename", PageName.TARIFFS.toString());
        logger.info("User (operator) went to view all tariffs page.");
        return "operator/tariffs";
    }

    @RequestMapping(value = "/operator/viewTariff", method = RequestMethod.POST)
    public String viewTariff(Model model, HttpServletRequest req) {
//        ControllerUtil.setRole(req);
        Long tariffId = Long.valueOf(req.getParameter("tariffId"));
        Tariff tariff = tariffService.getTariffById(tariffId);
        model.addAttribute("tariff", tariff);
        List<Option> optionsList = optionService.getAllOptionsForTariff(tariffId);
        model.addAttribute("optionsList", optionsList);
//        model.addAttribute("pagename", PageName.TARIFF.toString());
        logger.info("User (operator) went to view tariff page.");
        return "operator/viewTariff";
    }

    @RequestMapping(value = "/operator/deleteTariff", method = RequestMethod.POST)
    public String deleteTariff(Model model, HttpServletRequest req) {
//        ControllerUtil.setRole(req);
        Long tariffId = Long.valueOf(req.getParameter("tariffId"));
        List<Tariff> tariffs = null;
        try {
            tariffService.deleteTariff(tariffId);
            logger.info("Tariff with id: " + tariffId + " deleted from database.");
            tariffs = tariffService.getAllTariffs();
            model.addAttribute("tariffs", tariffs);
//            model.addAttribute("pagename", PageName.TARIFFS.toString());
            model.addAttribute("successmessage", "Tariff with id: " + tariffId + " deleted from database.");
            logger.info("User went to all tariffs page.");
            return "operator/tariffs";
        } catch (ECareException ecx) {
            tariffs = tariffService.getAllTariffs();
//            model.addAttribute("tariffs", tariffs);
//            req.setAttribute("pagename", PageName.TARIFFS.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/tariffsList";
        }
    }

    @RequestMapping(value = "operator/newTariff", method = RequestMethod.POST)
    public String newTariff(Model model, HttpServletRequest req) {
//        ControllerUtil.setRole(req);
//        req.setAttribute("pagename", PageName.NEW_TARIFF.toString());
        model.addAttribute("newTariff", new Tariff());
        logger.info("User (operator) went to create new tariff page.");
        return "operator/createTariff";
    }

    @RequestMapping(value = "/operator/createTariff", method = RequestMethod.POST)
    public String createTariff(@ModelAttribute("newTariff") Tariff tariff, Model model, HttpServletRequest req) {
        try {
            tariffService.add(tariff);
//            model.addAttribute("tariff", tariff);
            model.addAttribute("successmessage", "Tariff " + tariff.getTitle() + " created and saved in database.");
            logger.info("New tariff " + tariff + " created.");
            List<Tariff> tariffsList = tariffService.getAllTariffs();
            model.addAttribute("tariffsList", tariffsList);
            return "operator/tariffs";
        } catch (ECareException ecx) {
//            req.setAttribute("pagename", PageName.NEW_TARIFF.toString());
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/createTariff";
        }
    }

}
