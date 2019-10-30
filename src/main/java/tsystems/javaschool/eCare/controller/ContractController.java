package tsystems.javaschool.eCare.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import tsystems.javaschool.eCare.model.Client;
import tsystems.javaschool.eCare.model.Contract;
import tsystems.javaschool.eCare.model.Option;
import tsystems.javaschool.eCare.model.Tariff;
import tsystems.javaschool.eCare.service.ClientService;
import tsystems.javaschool.eCare.service.ContractService;
import tsystems.javaschool.eCare.service.TariffService;
import tsystems.javaschool.eCare.util.ControllerUtil;
import tsystems.javaschool.eCare.util.PageName;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Controller
@SessionAttributes("client")
public class ContractController {

    private static Logger logger = Logger.getLogger(ClientController.class);

    private ContractService contractService;

    @Autowired
    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    private TariffService tariffService;

    @Autowired
    public void setTariffService(TariffService tariffService) { this.tariffService = tariffService;}

    // Загрузка всех контрактов на главной странице
    @RequestMapping(value = "client/viewAllContractsForClient", method = RequestMethod.GET)
    public String viewAllContractsForClient(@ModelAttribute Client client, Model model) {
        List<Contract> contractsList = contractService.getAllContractsForClient(client.getId());
//        for(Contract c: contractsList) {
//            System.out.println(c.toString());
//        }
        model.addAttribute("contractsList", contractsList);
        return "client/contracts";
    }

    // Редактирование контракта с переходом на страницу "/show/{id}"
    @RequestMapping(value = "client/viewContract", method = RequestMethod.POST)
    public String viewContract(Model model, HttpServletRequest request) {
        Long contractId = Long.parseLong(request.getParameter("contractId"));
        Contract contract = contractService.getContractById(contractId);
        Set<Option> optionsList = contract.getOptions();
        model.addAttribute("contract", contract);
        model.addAttribute("optionsList", optionsList);
        return "client/viewContract";
    }

    @RequestMapping(value = "client/blockByClient", method = RequestMethod.POST)
    public String blockByClient(Model model, HttpServletRequest request) {
        try {
            Long contractId = Long.parseLong(request.getParameter("contractId"));
            Contract contract = contractService.getContractById(contractId);
            contractService.blockByClient(contract);
            logger.info("Contract " + contract + " is blocked by client.");
            return "redirect:/client/viewAllContractsForClient";
        } catch (Exception ecx){
            //req.setAttribute("client", contractService.getContractById(contractId).getClient());
            //req.setAttribute("pagename", PageName.CLIENT.toString());
            model.addAttribute("errormessage", ecx.getMessage());
            return "client/contracts";
        }
    }

    @RequestMapping(value = "client/unblockByClient", method = RequestMethod.POST)
    public String unblockByClient(Model model, HttpServletRequest request) {
        try {
            Long contractId = Long.parseLong(request.getParameter("contractId"));
            Contract contract = contractService.getContractById(contractId);
            contractService.unblockByClient(contract);
            logger.info("Contract " + contract + " is unblocked by client.");
            return "redirect:/client/viewAllContractsForClient";
        } catch (Exception ecx){
            // req.setAttribute("client", contractService.getContractById(contractId).getClient());
            // req.setAttribute("pagename", PageName.CLIENT.toString());
            model.addAttribute("errormessage", ecx.getMessage());
            return "client/contracts";
        }
    }

    @RequestMapping(value = "client/changeTariff", method = RequestMethod.POST)
    public String changeTariff(Model model, HttpServletRequest request) {
        Long contractId = Long.parseLong(request.getParameter("contractId"));
        Contract contract = contractService.getContractById(contractId);
        List<Tariff> tariffsList = tariffService.getAllTariffs();
        model.addAttribute("contract", contract);
        model.addAttribute("tariffsList", tariffsList);
        //model.addAttribute("pagename", PageName.CHOOSE_TARIFF.toString());
        logger.info("User went to change tariff page for contract " + contract + ".");
        return "client/chooseTariff";
    }
}
