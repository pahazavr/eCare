package tsystems.javaschool.eCare.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import tsystems.javaschool.eCare.util.PageName;
import tsystems.javaschool.eCare.validator.ContractValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@SessionAttributes({"client","role"})
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

    private ContractValidator contractValidator;

    @Autowired
    public void setContractValidator(ContractValidator contractValidator) {
        this.contractValidator = contractValidator;
    }

    @RequestMapping(value = "/operator/newContract", method = RequestMethod.POST)
    public String newContract(Model model, HttpServletRequest req) {
        logger.info("Operator went to create contract page.");
        Long clientId = Long.valueOf(req.getParameter("clientId"));
        Client client = clientService.getClientById(clientId);
        model.addAttribute("client", client);
        model.addAttribute("newContract", new Contract());

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.CLIENTS.toString());
        breadcrumb.add(PageName.VIEW_CLIENT.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.NEW_CONTRACT.toString());

        return "operator/createContract";
    }

    @RequestMapping(value = "/operator/createContract", method = RequestMethod.POST)
    public String createContract(@ModelAttribute("newContract") Contract contract, Model model, HttpServletRequest req, BindingResult bindingResult) {
        Long clientId = Long.valueOf(req.getParameter("clientId"));
        Long number = Long.parseLong(req.getParameter("number"));
        Client client = null;
        try {
            contractValidator.validate(contract, bindingResult);

            if (bindingResult.hasErrors()) {
                List<String> breadcrumb = new ArrayList<>();
                breadcrumb.add(PageName.HOME.toString());
                breadcrumb.add(PageName.CLIENTS.toString());
                breadcrumb.add(PageName.VIEW_CLIENT.toString());
                model.addAttribute("breadcrumb", breadcrumb);
                model.addAttribute("currentPage", PageName.NEW_CONTRACT.toString());

                return "operator/createContract";
            }

            logger.info("Creating of new contract with number: " + number + ".");


//            client.addContract(contract);
//            clientService.edit(client);
//
//            //Connecting of standard tariff for contract
//            contractService.add(contract);
//            contractService.setDefaultTariff(client, contract);

            client = clientService.getClientById(clientId);
            contract.setClient(client);
            contractService.add(contract);
            contractService.setDefaultTariff(client, contract);

            model.addAttribute("client", client);
            model.addAttribute("successmessage", "Contract " + contract.getNumber() + " with standard tariff created for client " + client.getFullName() + ".");
            logger.info("New contract: " + contract + " has created.");

            List<String> breadcrumb = new ArrayList<>();
            breadcrumb.add(PageName.HOME.toString());
            breadcrumb.add(PageName.CLIENTS.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.VIEW_CLIENT.toString());

            List<Contract> contractsList = contractService.getAllContractsForClient(client.getId());
            model.addAttribute("contractsList", contractsList);

            return "operator/viewClient";
        } catch (ECareException ecx) {
            List<String> breadcrumb = new ArrayList<>();
            breadcrumb.add(PageName.HOME.toString());
            breadcrumb.add(PageName.CLIENTS.toString());
            breadcrumb.add(PageName.VIEW_CLIENT.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.NEW_CONTRACT.toString());

            model.addAttribute("client", client);
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/createContract";
        }
    }

    @RequestMapping(value = "/operator/viewAllContracts", method = RequestMethod.POST)
    public String viewAllContracts(Model model, HttpServletRequest req) {
        List<Contract> contractsList = contractService.getAllContracts();
        model.addAttribute("contractsList", contractsList);

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.CONTRACTS.toString());

        logger.info("User (operator) went to view all contracts page.");
        return "operator/contracts";
    }

    @RequestMapping(value = "/operator/blockByOperator", method = RequestMethod.POST)
    public String blockByOperator(Model model, HttpServletRequest req) {
        Long contractId = Long.valueOf(req.getParameter("contractId"));
        try{
            Contract contract = contractService.getContractById(contractId);
            contractService.blockByOperator(contract);
            Client client = clientService.findClientByNumber(contract.getNumber());
            model.addAttribute("client", client);
            List<Contract> contractsList = contractService.getAllContractsForClient(client.getId());
            model.addAttribute("contractsList", contractsList);
            model.addAttribute("successmessage", "Contract " + contract.getNumber() + " blocked by operator.");
            logger.info("Contract " + contract + " is blocked by operator.");

            List<String> breadcrumb = new ArrayList<>();
            breadcrumb.add(PageName.HOME.toString());
            breadcrumb.add(PageName.CLIENTS.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.VIEW_CLIENT.toString());

            return "operator/viewClient";
        }catch (ECareException ecx){
            model.addAttribute("client", contractService.getContractById(contractId).getClient());
            model.addAttribute("errormessage", ecx.getMessage());

            List<String> breadcrumb = new ArrayList<>();
            breadcrumb.add(PageName.HOME.toString());
            breadcrumb.add(PageName.CLIENTS.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.VIEW_CLIENT.toString());

            return "operator/viewClient";
        }
    }

    @RequestMapping(value = "/operator/unblockByOperator", method = RequestMethod.POST)
    public String unblockByOperator(Model model, HttpServletRequest req) {
        Long contractId = Long.valueOf(req.getParameter("contractId"));
        try{
            Contract contract = contractService.getContractById(contractId);
            contractService.unblockByOperator(contract);
            Client client = clientService.findClientByNumber(contract.getNumber());
            model.addAttribute("client", client);

            List<Contract> contractsList = contractService.getAllContractsForClient(client.getId());
            model.addAttribute("contractsList", contractsList);

            List<String> breadcrumb = new ArrayList<>();
            breadcrumb.add(PageName.HOME.toString());
            breadcrumb.add(PageName.CLIENTS.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.VIEW_CLIENT.toString());

            model.addAttribute("successmessage", "Contract " + contract.getNumber() + " unblocked by operator.");
            logger.info("Contract " + contract + " is unblocked by operator.");
            return "operator/viewClient";
        }catch (ECareException ecx){
            model.addAttribute("client", contractService.getContractById(contractId).getClient());
            model.addAttribute("errormessage", ecx.getMessage());

            List<String> breadcrumb = new ArrayList<>();
            breadcrumb.add(PageName.HOME.toString());
            breadcrumb.add(PageName.CLIENTS.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.VIEW_CLIENT.toString());

            return "operator/viewClient";
        }
    }

    @RequestMapping(value = "/operator/viewContract", method = RequestMethod.POST)
    public String viewContractByOperator(Model model, HttpServletRequest request) {
        Long contractId = Long.parseLong(request.getParameter("contractId"));
        Contract contract = contractService.getContractById(contractId);
        Long clientId = Long.parseLong(request.getParameter("clientId"));
        Client client = clientService.getClientById(clientId);
        Set<Option> optionsList = contract.getOptions();
        model.addAttribute("client", client);
        model.addAttribute("contract", contract);
        model.addAttribute("optionsList", optionsList);

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.CLIENTS.toString());
        breadcrumb.add(PageName.VIEW_CLIENT.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.VIEW_CONTRACT.toString());

        return "operator/viewContract";
    }

    @RequestMapping(value = "operator/changeTariff", method = RequestMethod.POST)
    public String changeTariffByOperator(Model model, HttpServletRequest request) {
        Long contractId = Long.parseLong(request.getParameter("contractId"));
        Long clientId = Long.parseLong(request.getParameter("clientId"));
        Client client = clientService.getClientById(clientId);
        Contract contract = contractService.getContractById(contractId);
        List<Tariff> tariffsList = tariffService.getAllTariffs();
        model.addAttribute("client", client);
        model.addAttribute("contract", contract);
        model.addAttribute("tariffsList", tariffsList);

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.CLIENTS.toString());
        breadcrumb.add(PageName.VIEW_CLIENT.toString());
        breadcrumb.add(PageName.VIEW_CONTRACT.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.CHANGE_TARIFF.toString());

        logger.info("Operator went to change tariff page for contract " + contract + ".");
        return "operator/chooseTariff";
    }

    @RequestMapping(value = "operator/chooseTariff", method = RequestMethod.POST)
    public String chooseTariffByOperator(Model model, HttpServletRequest request) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.CLIENTS.toString());
        breadcrumb.add(PageName.VIEW_CLIENT.toString());
        breadcrumb.add(PageName.VIEW_CONTRACT.toString());

        Long clientId = Long.parseLong(request.getParameter("clientId"));
        Long contractId = Long.valueOf(request.getParameter("contractId"));
        Long tariffId = Long.valueOf(request.getParameter("tariffId"));
        Contract contract = null;
        try {
            Client client = clientService.getClientById(clientId);
            contract = contractService.getContractById(contractId);
            Tariff tariff = tariffService.getTariffById(tariffId);
            List<Option> options = optionService.getAllOptionsForTariff(tariffId);
            model.addAttribute("client", client);
            model.addAttribute("contract", contract);
            model.addAttribute("tariff", tariff);
            model.addAttribute("options", options);

            breadcrumb.add(PageName.CHANGE_TARIFF.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.CHOOSE_OPTIONS.toString());
            return "operator/chooseOptions";
        } catch (ECareException ecx) {
            model.addAttribute("currentPage", PageName.CHANGE_TARIFF.toString());

            contract = contractService.getContractById(contractId);
            List<Tariff> tariffs = tariffService.getAllTariffs();
            model.addAttribute("contract", contract);
            model.addAttribute("tariffs", tariffs);
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/chooseTariff";
        }
    }

    @RequestMapping(value = "operator/setNewTariff", method = RequestMethod.POST)
    public String setNewTariffByOperator(@ModelAttribute Client client, Model model, HttpServletRequest request) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.CLIENTS.toString());
        breadcrumb.add(PageName.VIEW_CLIENT.toString());

        Long contractId = Long.valueOf(request.getParameter("contractId"));
        Long tariffId = Long.valueOf(request.getParameter("tariffId"));
        String[] chosenOptionsArray = request.getParameterValues("options");
        Contract contract = contractService.getContractById(contractId);
        try {
            Tariff tariff = tariffService.getTariffById(tariffId);
            contract = contractService.setTariff(client, contract, tariff, chosenOptionsArray);
            model.addAttribute("contract", contract);
            Set<Option> optionsList = contract.getOptions();
            model.addAttribute("optionsList", optionsList);
            model.addAttribute("successmessage", "Tariff " + tariff.getTitle() + " is set to contract " + contract.getNumber() + ".");
            logger.info("In contract " + contract + "set new tariff " + tariff + ".");

            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.VIEW_CONTRACT.toString());

            return "client/viewContract";
        } catch (ECareException ecx) {
            tariffId = Long.valueOf(request.getParameter("tariffId"));
            Tariff tariff = tariffService.getTariffById(tariffId);
            List<Option> options = optionService.getAllOptionsForTariff(tariffId);
            model.addAttribute("contract", contract);
            model.addAttribute("tariff", tariff);
            model.addAttribute("options", options);

            breadcrumb.add(PageName.VIEW_CONTRACT.toString());
            breadcrumb.add(PageName.CHANGE_TARIFF.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.CHOOSE_OPTIONS.toString());

            model.addAttribute("errormessage", ecx.getMessage());
            return "client/chooseOptions";
        }
    }

    @RequestMapping(value = "/operator/deleteContractForClient", method = RequestMethod.POST)
    public String deleteContract(Model model, HttpServletRequest req) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.CLIENTS.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.VIEW_CLIENT.toString());

        Long contractId = Long.valueOf(req.getParameter("contractId"));
        try{
            Contract contract = contractService.getContractById(contractId);
            Client client = contract.getClient();
            client.getContracts().remove(contract);
            clientService.edit(client);
            model.addAttribute("client", client);
            model.addAttribute("successmessage", "Contract " + contract.getNumber() + " deleted from database.");
            logger.info("User (operator) deleted contract with id: " + contractId + " from database.");

            return "operator/viewClient";
        }catch (ECareException ecx){
            model.addAttribute("client", contractService.getContractById(contractId).getClient());
            model.addAttribute("errormessage", ecx.getMessage());
            return "operator/viewClient";
        }
    }

    @RequestMapping(value = "client/viewAllContractsForClient", method = RequestMethod.POST)
    public String viewAllContractsForClient(@ModelAttribute Client client, Model model) {
        List<Contract> contractsList = contractService.getAllContractsForClient(client.getId());
        model.addAttribute("contractsList", contractsList);

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.CONTRACTS.toString());

        return "client/contracts";
    }

    @RequestMapping(value = "client/viewContract", method = RequestMethod.POST)
    public String viewContractByClient(Model model, HttpServletRequest request) {
        Long contractId = Long.parseLong(request.getParameter("contractId"));
        Contract contract = contractService.getContractById(contractId);
        model.addAttribute("contract", contract);
        Set<Option> optionsList = contract.getOptions();
        model.addAttribute("optionsList", optionsList);

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.CONTRACTS.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.VIEW_CONTRACT.toString());

        return "client/viewContract";
    }

    @RequestMapping(value = "client/blockByClient", method = RequestMethod.POST)
    public String blockByClient(@ModelAttribute Client client, Model model, HttpServletRequest request) {
        List<String> breadcrumb = new ArrayList<>();
        try {
            Long contractId = Long.parseLong(request.getParameter("contractId"));
            Contract contract = contractService.getContractById(contractId);
            contractService.blockByClient(contract);
            logger.info("Contract " + contract + " is blocked by client.");

            List<Contract> contractsList = contractService.getAllContractsForClient(client.getId());
            model.addAttribute("contractsList", contractsList);

            breadcrumb.add(PageName.HOME.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.CONTRACTS.toString());

            return "client/contracts";
        } catch (Exception ecx){
            List<Contract> contractsList = contractService.getAllContractsForClient(client.getId());
            model.addAttribute("contractsList", contractsList);

            breadcrumb.add(PageName.HOME.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.CONTRACTS.toString());

            model.addAttribute("errormessage", ecx.getMessage());
            return "client/contracts";
        }
    }

    @RequestMapping(value = "client/unblockByClient", method = RequestMethod.POST)
    public String unblockByClient(@ModelAttribute Client client, Model model, HttpServletRequest request) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.CONTRACTS.toString());

        try {
            Long contractId = Long.parseLong(request.getParameter("contractId"));
            Contract contract = contractService.getContractById(contractId);
            System.out.println(contract.toString());
            contractService.unblockByClient(contract);
            logger.info("Contract " + contract + " is unblocked by client.");
            List<Contract> contractsList = contractService.getAllContractsForClient(client.getId());
            model.addAttribute("contractsList", contractsList);
            return "client/contracts";
        } catch (Exception ecx){
            List<Contract> contractsList = contractService.getAllContractsForClient(client.getId());
            model.addAttribute("contractsList", contractsList);
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

        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.CONTRACTS.toString());
        breadcrumb.add(PageName.VIEW_CONTRACT.toString());
        model.addAttribute("breadcrumb", breadcrumb);
        model.addAttribute("currentPage", PageName.CHANGE_TARIFF.toString());

        logger.info("User went to change tariff page for contract " + contract + ".");
        return "client/chooseTariff";
    }

    @RequestMapping(value = "client/chooseTariff", method = RequestMethod.POST)
    public String chooseTariff(Model model, HttpServletRequest request) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.CONTRACTS.toString());
        breadcrumb.add(PageName.VIEW_CONTRACT.toString());


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

            breadcrumb.add(PageName.CHANGE_TARIFF.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.CHOOSE_OPTIONS.toString());
            return "client/chooseOptions";
        } catch (ECareException ecx) {
            contract = contractService.getContractById(contractId);
            List<Tariff> tariffs = tariffService.getAllTariffs();
            model.addAttribute("contract", contract);
            model.addAttribute("tariffs", tariffs);
            model.addAttribute("currentPage", PageName.CHANGE_TARIFF.toString());
            model.addAttribute("errormessage", ecx.getMessage());
            return "client/chooseTariff";
        }
    }

    @RequestMapping(value = "client/setNewTariff", method = RequestMethod.POST)
    public String setNewTariff(@ModelAttribute Client client, Model model, HttpServletRequest request) {
        List<String> breadcrumb = new ArrayList<>();
        breadcrumb.add(PageName.HOME.toString());
        breadcrumb.add(PageName.CONTRACTS.toString());

        Long contractId = Long.valueOf(request.getParameter("contractId"));
        Long tariffId = Long.valueOf(request.getParameter("tariffId"));
        String[] chosenOptionsArray = request.getParameterValues("options");
        Contract contract = contractService.getContractById(contractId);
        try {
            Tariff tariff = tariffService.getTariffById(tariffId);
            contract = contractService.setTariff(client, contract, tariff, chosenOptionsArray);
            model.addAttribute("contract", contract);
            Set<Option> optionsList = contract.getOptions();
            model.addAttribute("optionsList", optionsList);
            model.addAttribute("successmessage", "Tariff " + tariff.getTitle() + " is set to contract " + contract.getNumber() + ".");
            logger.info("In contract " + contract + "set new tariff " + tariff + ".");


            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.VIEW_CONTRACT.toString());

            return "client/viewContract";
        } catch (ECareException ecx) {
            tariffId = Long.valueOf(request.getParameter("tariffId"));
            Tariff tariff = tariffService.getTariffById(tariffId);
            List<Option> options = optionService.getAllOptionsForTariff(tariffId);
            model.addAttribute("contract", contract);
            model.addAttribute("tariff", tariff);
            model.addAttribute("options", options);

            breadcrumb.add(PageName.VIEW_CONTRACT.toString());
            breadcrumb.add(PageName.CHANGE_TARIFF.toString());
            model.addAttribute("breadcrumb", breadcrumb);
            model.addAttribute("currentPage", PageName.CHOOSE_OPTIONS.toString());

            model.addAttribute("errormessage", ecx.getMessage());
            return "client/chooseOptions";
        }
    }
}
