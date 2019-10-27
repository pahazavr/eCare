package tsystems.javaschool.eCare.service;

import tsystems.javaschool.eCare.model.Client;

import java.util.List;

public interface ClientService {
    void add(Client client);
    void edit(Client client);
    Client getClientById(Long id);
    void delete(Long id);
    List<Client> getAllClients();
    void deleteAllClients();
    Long getNumberOfClients();
    Client findClientByNumber(Long number);
    Client findClientByEmail (String email);
    Client findClientByLoginAndPassword(String login, String password);
}
