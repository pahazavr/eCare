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
import tsystems.javaschool.eCare.util.PageName;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
        List<Tariff> tariffsList = tariffService.getAllTariffs();
        model.addAttribute("tariffsList", tariffsList);
        logger.info("User (operator) went to view all tariffs page.");

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.TARIFFS.toString());

        return "operator/tariffs";
    }

    @RequestMapping(value = "/operator/viewTariff", method = RequestMethod.POST)
    public String viewTariff(Model model, HttpServletRequest req) {
        Long tariffId = Long.valueOf(req.getParameter("tariffId"));
        Tariff tariff = tariffService.getTariffById(tariffId);
        model.addAttribute("tariff", tariff);
        List<Option> optionsList = optionService.getAllOptionsForTariff(tariffId);
        model.addAttribute("optionsList", optionsList);
        logger.info("User (operator) went to view tariff page.");

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.TARIFFS.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.VIEW_TARIFF.toString());

        return "operator/viewTariff";
    }

    @RequestMapping(value = "/operator/deleteTariff", method = RequestMethod.POST)
    public String deleteTariff(Model model, HttpServletRequest req) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.TARIFFS.toString());

        Long tariffId = Long.valueOf(req.getParameter("tariffId"));
        List<Tariff> tariffs = null;
        try {
            tariffService.deleteTariff(tariffId);
            logger.info("Tariff with id: " + tariffId + " deleted from database.");
            tariffs = tariffService.getAllTariffs();
            model.addAttribute("tariffsList", tariffs);
            model.addAttribute("successmessage", "Tariff with id: " + tariffId + " deleted from database.");
            logger.info("User went to all tariffs page.");
            return "operator/tariffs";
        } catch (ECareException ecx) {
            tariffs = tariffService.getAllTariffs();
            model.addAttribute("tariffs", tariffs);
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/tariffsList";
        }
    }

    @RequestMapping(value = "operator/newTariff", method = RequestMethod.POST)
    public String newTariff(Model model, HttpServletRequest req) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.TARIFFS.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.NEW_TARIFF.toString());

        model.addAttribute("newTariff", new Tariff());
        logger.info("User (operator) went to create new tariff page.");
        return "operator/createTariff";
    }

    @RequestMapping(value = "/operator/createTariff", method = RequestMethod.POST)
    public String createTariff(@ModelAttribute("newTariff") Tariff tariff, Model model, HttpServletRequest req) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());

        try {
            tariffService.add(tariff);
            model.addAttribute("successmessage", "Tariff " + tariff.getTitle() + " created and saved in database.");
            logger.info("New tariff " + tariff + " created.");
            List<Tariff> tariffsList = tariffService.getAllTariffs();
            model.addAttribute("tariffsList", tariffsList);

            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.TARIFFS.toString());

            return "operator/tariffs";
        } catch (ECareException ecx) {
            breadcrumb.add(PageName.TARIFFS.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.NEW_TARIFF.toString());
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/createTariff";
        }
    }

}
