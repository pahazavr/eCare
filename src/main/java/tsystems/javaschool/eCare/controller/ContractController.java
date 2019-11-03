package tsystems.javaschool.eCare.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import tsystems.javaschool.eCare.ECareException;
import tsystems.javaschool.eCare.model.Client;
import tsystems.javaschool.eCare.model.Contract;
import tsystems.javaschool.eCare.model.Option;
import tsystems.javaschool.eCare.model.Tariff;
import tsystems.javaschool.eCare.service.ClientService;
import tsystems.javaschool.eCare.service.ContractService;
import tsystems.javaschool.eCare.service.OptionService;
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

    private OptionService optionService;

    @Autowired
    public void setOptionService(OptionService optionService) { this.optionService = optionService;}

    @RequestMapping(value = "/newContract", method = RequestMethod.POST)
    public String newContract(Model model, HttpServletRequest req) {
        Long clientId = Long.valueOf(req.getParameter("id"));
        Client client = clientService.getClientById(clientId);
        model.addAttribute("client", client);
        model.addAttribute("newContract", new Contract());
//        model.addAttribute("pagename", PageName.NEW_CONTRACT.toString());
        logger.info("User (operator) went to create contract page.");
        return "operator/createContract";
    }

    @RequestMapping(value = "/operator/createContract", method = RequestMethod.POST)
    public String createContract(@ModelAttribute("newContract") Contract contract, Model model, HttpServletRequest req) {
        Long clientId = Long.valueOf(req.getParameter("id"));
//        ControllerUtil.setRole(req);
        Client client = clientService.getClientById(clientId);
        model.addAttribute("client", client);
        try {
            logger.info("Creating of new contract with number: " + req.getParameter("number") + ".");
//            Long number = Util.checkNumberOnExisting(req.getParameter("number"));
//            Contract contract = new Contract(client, number, null, false, false);
            Long number = Long.parseLong(req.getParameter("number"));
            contractService.add(contract);
            client.addContract(contract);
            clientService.edit(client);

            //Connecting of standard tariff for contract
//            contract = contractService.findContractByNumber(number);
            contractService.setDefaultTariff(contract);
//            client = clientService.getClientById(clientId);

            model.addAttribute("client", client);
//            model.addAttribute("pagename", PageName.CLIENT.toString());
            model.addAttribute("successmessage", "Contract " + contract.getNumber() + " with standard tariff created for client " + client.getFullName() + ".");
            logger.info("New contract: " + contract + " has created.");
            return "operator/viewClient";
        } catch (ECareException ecx) {
            model.addAttribute("client", client);
//            model.addAttribute("pagename", PageName.NEW_CONTRACT.toString());
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/createContract";
        }
    }

    @RequestMapping(value = "/operator/blockByOperator", method = RequestMethod.POST)
    public String blockByOperator(Model model, HttpServletRequest req) {
        Long contractId = Long.valueOf(req.getParameter("contractId"));
//        ControllerUtil.setRole(req);
        try{
            Contract contract = contractService.getContractById(contractId);
            contractService.blockByOperator(contract);
            Client client = clientService.findClientByNumber(contract.getNumber());
            model.addAttribute("client", client);
//            model.addAttribute("pagename", PageName.CLIENT.toString());
            model.addAttribute("successmessage", "Contract " + contract.getNumber() + " blocked by operator.");
            logger.info("Contract " + contract + " is blocked by operator.");
            return "operator/viewClient";
        }catch (ECareException ecx){
//            req.setAttribute("client", contractService.getContractById(contractId).getClient());
//            req.setAttribute("pagename", PageName.CLIENT.toString());
//            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/viewClient";
        }
    }

    @RequestMapping(value = "/operator/unblockByOperator", method = RequestMethod.POST)
    public String unblockByOperator(Model model, HttpServletRequest req) {
        Long contractId = Long.valueOf(req.getParameter("contractId"));
//        ControllerUtil.setRole(req);
        try{
            Contract contract = contractService.getContractById(contractId);
            contractService.unblockByOperator(contract);
            Client client = clientService.findClientByNumber(contract.getNumber());
            model.addAttribute("client", client);
//            req.setAttribute("pagename", PageName.CLIENT.toString());
            model.addAttribute("successmessage", "Contract " + contract.getNumber() + " unblocked by operator.");
            logger.info("Contract " + contract + " is unblocked by operator.");
            return "operator/viewClient";
        }catch (ECareException ecx){
//            req.setAttribute("client", contractService.loadContract(contractId).getClient());
//            req.setAttribute("pagename", PageName.CLIENT.toString());
//            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/viewClient";
        }
    }

    @RequestMapping(value = "/operator/viewContract", method = RequestMethod.POST)
    public String viewContractByOperator(Model model, HttpServletRequest request) {
        Long contractId = Long.parseLong(request.getParameter("contractId"));
        Contract contract = contractService.getContractById(contractId);
        Set<Option> optionsList = contract.getOptions();
        model.addAttribute("contract", contract);
        model.addAttribute("optionsList", optionsList);
        return "operator/viewContract";
    }

    @RequestMapping(value = "/operator/deleteContractForClient", method = RequestMethod.POST)
    public String deleteContract(Model model, HttpServletRequest req) {
        Long contractId = Long.valueOf(req.getParameter("contractId"));
//        ControllerUtil.setRole(req);
        try{
            Contract contract = contractService.getContractById(contractId);
            Client client = contract.getClient();
            client.getContracts().remove(contract);
            clientService.edit(client);
            model.addAttribute("client", client);
//            model.addAttribute("pagename", PageName.CLIENT.toString());
            model.addAttribute("successmessage", "Contract " + contract.getNumber() + " deleted from database.");
            logger.info("User (operator) deleted contract with id: " + contractId + " from database.");
            return "operator/viewClient";
        }catch (ECareException ecx){
//            req.setAttribute("client", contractService.getContractById(contractId).getClient());
//            req.setAttribute("pagename", PageName.CLIENT.toString());
//            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/viewClient";
        }
    }

    @RequestMapping(value = "client/viewAllContractsForClient", method = RequestMethod.GET)
    public String viewAllContractsForClient(@ModelAttribute Client client, Model model) {
        List<Contract> contractsList = contractService.getAllContractsForClient(client.getId());
        model.addAttribute("contractsList", contractsList);
        return "client/contracts";
    }

    @RequestMapping(value = "client/viewContract", method = RequestMethod.POST)
    public String viewContractByClient(Model model, HttpServletRequest request) {
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
            System.out.println(contract.toString());
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

    @RequestMapping(value = "client/chooseTariff", method = RequestMethod.POST)
    public String chooseTariff(Model model, HttpServletRequest request) {
        Long contractId = Long.valueOf(request.getParameter("contractId"));
        Long tariffId = Long.valueOf(request.getParameter("tariffId"));
        Contract contract = null;
        try {
            contract = contractService.getContractById(contractId);
            Tariff tariff = tariffService.getTariffById(tariffId);
            List<Option> options = optionService.getAllOptionsForTariff(tariffId);
            model.addAttribute("contract", contract);
            model.addAttribute("tariff", tariff);
            model.addAttribute("options", options);
//            req.setAttribute("pagename", PageName.CHOOSE_OPTIONS.toString());
            return "client/chooseOptions";
        } catch (ECareException ecx) {
            contract = contractService.getContractById(contractId);
            List<Tariff> tariffs = tariffService.getAllTariffs();
            model.addAttribute("contract", contract);
            model.addAttribute("tariffs", tariffs);
//            model.addAttribute("pagename", PageName.CHOOSE_TARIFF.toString());
            model.addAttribute("errormessage", ecx.getMessage());
            return "client/chooseTariff";
        }
    }

    @RequestMapping(value = "client/setNewTariff", method = RequestMethod.POST)
    public String setNewTariff(Model model, HttpServletRequest request) {
        Long contractId = Long.valueOf(request.getParameter("contractId"));
        Long tariffId = Long.valueOf(request.getParameter("tariffId"));
        String[] chosenOptionsArray = request.getParameterValues("options");
        Contract contract = contractService.getContractById(contractId);
        try {
            Tariff tariff = tariffService.getTariffById(tariffId);
            contract = contractService.setTariff(contract, tariff, chosenOptionsArray);
            model.addAttribute("contract", contract);
//            req.setAttribute("pagename", PageName.CONTRACT.toString());
            model.addAttribute("successmessage", "Tariff " + tariff.getTitle() + " is set to contract " + contract.getNumber() + ".");
            logger.info("In contract " + contract + "set new tariff " + tariff + ".");
            return "client/viewContract";
        } catch (ECareException ecx) {
            tariffId = Long.valueOf(request.getParameter("tariffId"));
            Tariff tariff = tariffService.getTariffById(tariffId);
            List<Option> options = optionService.getAllOptionsForTariff(tariffId);
            model.addAttribute("contract", contract);
            model.addAttribute("tariff", tariff);
            model.addAttribute("options", options);
//            req.setAttribute("pagename", PageName.CHOOSE_OPTIONS);
            model.addAttribute("errormessage", ecx.getMessage());
            return "client/chooseOptions";
        }
    }
}
