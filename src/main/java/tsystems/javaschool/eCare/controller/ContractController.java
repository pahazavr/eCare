package tsystems.javaschool.eCare.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import tsystems.javaschool.eCare.model.Client;
import tsystems.javaschool.eCare.model.Contract;
import tsystems.javaschool.eCare.service.ClientService;
import tsystems.javaschool.eCare.service.ContractService;
import tsystems.javaschool.eCare.util.ControllerUtil;
import tsystems.javaschool.eCare.util.PageName;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/contracts")
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

    // Загрузка всех контрактов на главной странице
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String viewContracts(HttpServletRequest req) {
        Long contractId = Long.valueOf(req.getParameter("id"));
        ControllerUtil.setRole(req);
        Contract contract = contractService.getContractById(contractId);
        req.setAttribute("contract", contract);
        req.setAttribute("client", contract.getClient());
        //req.setAttribute("pagename", PageName.CONTRACT.toString());
        return "client/contracts";
    }

    // Редактирование контракта с переходом на страницу "/show/{id}"
    @RequestMapping(value = "/show/", method = RequestMethod.GET)
    public String editPage(HttpServletRequest req) {
        Long contractId = Long.valueOf(req.getParameter("id"));
        Contract contract = contractService.getContractById(contractId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("client/showContract");
        modelAndView.addObject("contract", contract);
        return "client/showContract";
    }

    @RequestMapping(value = "/blockByClient", method = RequestMethod.POST)
    public String blockByClient(HttpServletRequest req) {
        Long contractId = Long.parseLong(req.getParameter("id"));
        ControllerUtil.setRole(req);
        try {
            Contract contract = contractService.getContractById(contractId);
            contractService.blockByClient(contract);
            Client client = clientService.findClientByNumber(contract.getNumber());
            req.setAttribute("client", client);
            //req.setAttribute("pagename", PageName.CLIENT.toString());
            //req.setAttribute("successmessage", "Contract " + contract.getNumber() + " blocked by client.");
            logger.info("Contract " + contract + " is blocked by client.");
            return "client/contracts";
        } catch (Exception ecx){
            req.setAttribute("client", contractService.getContractById(contractId).getClient());
           // req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "client/contracts";
        }
    }

    @RequestMapping(value = "/unblockByClient", method = RequestMethod.POST)
    public String unblockByClient(HttpServletRequest req) {
        Long contractId = Long.parseLong(req.getParameter("id"));
        ControllerUtil.setRole(req);
        try {
            Contract contract = contractService.getContractById(contractId);
            contractService.unblockByClient(contract);
            Client client = clientService.findClientByNumber(contract.getNumber());
            req.setAttribute("client", client);
           // req.setAttribute("pagename", PageName.CLIENT.toString());
           // req.setAttribute("successmessage", "Contract " + contract.getNumber() + " unblocked by client.");
            logger.info("Contract " + contract + " is unblocked by client.");
            return "client/contracts";
        } catch (Exception ecx){
            req.setAttribute("client", contractService.getContractById(contractId).getClient());
            req.setAttribute("pagename", PageName.CLIENT.toString());
            req.setAttribute("errormessage", ecx.getMessage());
            return "client/contracts";
        }
    }
}
