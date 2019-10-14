package tsystems.javaschool.eCare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tsystems.javaschool.eCare.dao.ClientDAO;
import tsystems.javaschool.eCare.model.Client;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientDAO clientDAO;

    @Autowired
    public void setTariffDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    @Transactional
    public void add(Client client) {
        clientDAO.add(client);
    }

    @Override
    @Transactional
    public Client getById(int id) {
         return clientDAO.getById(id);
    }
}
