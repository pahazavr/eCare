package tsystems.javaschool.eCare.dao;

import tsystems.javaschool.eCare.model.Client;

public interface ClientDAO {
    void add(Client client);
    Client getById(Long id);
    Client findByUsername (String username);
}
