package tsystems.javaschool.eCare.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tsystems.javaschool.eCare.ECareException;
import tsystems.javaschool.eCare.dao.ClientDAO;
import tsystems.javaschool.eCare.dao.RoleDAO;
import tsystems.javaschool.eCare.model.Client;
import tsystems.javaschool.eCare.model.Role;

import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

    private static Logger logger = Logger.getLogger(ClientService.class);

    private ClientDAO clientDAO;
    @Autowired
    public void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    private RoleDAO roleDAO;
    @Autowired
    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void add(Client client) throws ECareException {
        logger.info("Add client " + client + " in DB.");
        try {
            client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
            Set<Role> roles = new HashSet<>();
            roles.add(roleDAO.getById(1L));
            client.setRoles(roles);
            clientDAO.add(client);
            logger.info("Client " + client + " added in DB.");
        } catch (Exception ex) {
            ECareException eCareException = new ECareException("Failed to add client " + client + " in DB.", ex);
            logger.warn(eCareException.getMessage(), ex);
            throw eCareException;
        }
    }

    @Override
    @Transactional
    public void edit(Client client) throws ECareException {
        logger.info("Update client " + client + " in DB.");
        try {
            clientDAO.edit(client);
            logger.info("Client " + client + " updated in DB.");
        } catch (Exception ex) {
            ECareException eCareException = new ECareException("Failed to update client " + client + " in DB.", ex);
            logger.warn(eCareException.getMessage(), ex);
            throw eCareException;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ECareException {
        logger.info("Delete client with id: " + id + " from DB.");
        Client client = clientDAO.getClientById(id);
        // If DAO returns null method will throws an ECareException.
        if(client == null) {
            ECareException eCareException = new ECareException("Client with id = " + id + " not exist.");
            logger.warn(eCareException.getMessage(), eCareException);
            throw eCareException;
        }
        // Else client will be deleted from the database.
        clientDAO.delete(client);
        logger.info("Client " + client + " deleted from DB.");
        clientDAO.delete(client);
    }

    @Override
    @Transactional
    public List<Client> getAllClients() throws ECareException {
        logger.info("Get all clients from DB.");
        List<Client> clients = clientDAO.getAllClients();
        // If DAO returns null method will throws an ECareException.
        if(clients == null) {
            ECareException eCareException = new ECareException("Failed to get all clients from DB.");
            logger.error(eCareException.getMessage(), eCareException);
            throw eCareException;
        }
        logger.info("All clients obtained from DB.");
        // Else method returns list of client entities
        return clients;
    }

    @Override
    @Transactional
    public void deleteAllClients() {
        logger.info("Delete all clients from DB.");
        clientDAO.deleteAllClients();
        logger.info("All clients deleted from DB.");
    }

    @Override
    @Transactional
    public Long getNumberOfClients() {
        logger.info("Get number of clients in DB.");
        Long number = clientDAO.getSize();
        logger.info(number + " clients obtained from DB.");
        return number;
    }

    @Override
    @Transactional
    public Client getClientById(Long id) throws ECareException {
        logger.info("Get client with id: " + id + " from DB.");
        Client client = clientDAO.getClientById(id);
        //If DAO returns null method will throws an ECareException
        if(client == null) {
            ECareException ecx = new ECareException("Client with id = " + id + " not found in DB.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("Client " + client + " got from DB.");
        //else method returns client entity
        return client;
    }

    @Override
    @Transactional
    public Client findClientByNumber(Long number) throws ECareException {
        logger.info("Find client with telephone number: " + number + " in DB.");
        Client client = null;
        try {
            // Search of client in the database by DAO method.
            client = clientDAO.findClientByNumber(number);
            // If client does not exist in database, block "catch" catches the NoResultException and
            // throws an ECareException.
        } catch(NoResultException nrx) {
            ECareException ecx = new ECareException("Client with number: " + number + " not found.", nrx);
            logger.warn(ecx.getMessage(), nrx);
            throw ecx;
        }
        logger.info("Client " + client + " found and loaded from DB.");
        return client;
    }

    @Override
    @Transactional
    public Client findClientByEmail(String username) throws ECareException {
        logger.info("Find client with Email: " + username + " in DB.");
        Client client = null;
        try {
            // Search of client in the database by DAO method.
            client = clientDAO.findClientByEmail(username);
            // If client does not exist in database, block "catch" catches the NoResultException and
            // throws an ECareException.
        } catch(NoResultException nrx) {
            ECareException ecx = new ECareException("Client with Email: " + username + " not found.", nrx);
            logger.warn(ecx.getMessage(), nrx);
            throw ecx;
        }
        logger.info("Client " + client + " found and loaded from DB.");
        return client;
    }

    @Override
    @Transactional
    public Client findClientByLoginAndPassword(String login, String password) throws ECareException {
        logger.info("Find client with login: " + login + " and password:" + password + " in DB.");
        Client client = null;
        try {
            // Searching of client in the database by DAO method.
            client = clientDAO.findClientByLoginAndPassword(login, password);
            // If client does not exist in database, block try catches the NoResultException and
            // throws an ECareException.
        } catch(NoResultException nrx) {
            ECareException ecx = new ECareException("Incorrect login/password or client does not exist.", nrx);
            logger.warn(ecx.getMessage(), nrx);
            throw ecx;
        }
        logger.info("Client " + client + " found and loaded from DB.");
        return client;
    }
}