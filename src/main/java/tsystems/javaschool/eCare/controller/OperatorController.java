package tsystems.javaschool.eCare.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tsystems.javaschool.eCare.ECareException;
import tsystems.javaschool.eCare.model.Client;
import tsystems.javaschool.eCare.model.Contract;
import tsystems.javaschool.eCare.service.ClientService;
import tsystems.javaschool.eCare.service.ContractService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OperatorController {
    private static Logger logger = Logger.getLogger(OperatorController.class);

    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    private ContractService contractService;

    @Autowired
    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    @RequestMapping(value = "/operator/viewAllClients", method = RequestMethod.POST)
    public String viewAllClients(Model model, HttpServletRequest req) {
//        ControllerUtil.setRole(req);
        List<Client> clientsList = clientService.getAllClients();
        model.addAttribute("clientsList", clientsList);
//        req.setAttribute("pagename", PageName.DASHBOARD.toString());
        logger.info("Operator went to view dashboard page.");
        return "operator/clients";
    }

    @RequestMapping(value = "/operator/viewClient", method = RequestMethod.POST)
    public String viewClient(Model model, HttpServletRequest request) {
        try {
            Long clientId = Long.parseLong(request.getParameter("clientId"));
            Client client = clientService.getClientById(clientId);
            logger.info("Operator went to profile page of client: " + client + ".");
            model.addAttribute("client", client);

            List<Contract> contractsList = contractService.getAllContractsForClient(clientId);
            model.addAttribute("contractsList", contractsList);
            return "operator/viewClient";
        } catch (ECareException ecx) {
//            req.setAttribute("pagename", PageName.DASHBOARD.toString());
//            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/viewClient";
        }
    }

    @RequestMapping(value = "/operator/searchClientByNumber", method = RequestMethod.POST)
    public String searchClientByNumber( Model model, HttpServletRequest request) {
//        ControllerUtil.setRole(req);
        List<Client> clientsList = null;
        try {
//            Long number = Util.checkLong(req.getParameter("number"));
            Long number = Long.parseLong(request.getParameter("number"));
            logger.info("User searching of client by number " + number + ".");
            Client client = clientService.findClientByNumber(number);
            model.addAttribute("client", client);
//            req.setAttribute("pagename", PageName.CLIENT.toString());
//            req.setAttribute("successmessage", "Client " + client.getName() + " found and loaded from database.");
            logger.info("User went to client page.");
            return "client/profile";
        } catch (ECareException ecx) {
            clientsList = clientService.getAllClients();
            model.addAttribute("clientsList", clientsList);
//            req.setAttribute("pagename", PageName.DASHBOARD.toString());
//            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/clients";
        }
    }

    @RequestMapping(value = "/operator/deleteClient", method = RequestMethod.POST)
    public String deleteClient(Model model, HttpServletRequest req) {
//        ControllerUtil.setRole(req);
        Long clientId = Long.valueOf(req.getParameter("id"));
        List<Client> clientsList = null;
        try{
            clientService.delete(clientId);
            logger.info("User delete are client with id:" + clientId + " from database.");
            clientsList  = clientService.getAllClients();
//            req.setAttribute("successmessage", "Client with id: " + clientId + " deleted from database.");
            model.addAttribute("clientsList", clientsList);
//            req.setAttribute("pagename", PageName.DASHBOARD.toString());
            logger.info("User went to view dashboard page.");
            return "operator/dashboard";
        }catch (ECareException ecx) {
            clientsList = clientService.getAllClients();
            model.addAttribute("clientsList", clientsList);
//            req.setAttribute("pagename", PageName.DASHBOARD.toString());
//            req.setAttribute("errormessage", ecx.getMessage());
            return "operator/dashboard";
        }
    }
}
