package tsystems.javaschool.eCare.service;

import tsystems.javaschool.eCare.model.Client;

public interface ClientService {
    void add(Client client);
    Client getById(Long id);
    Client findByUsername (String username);
}
