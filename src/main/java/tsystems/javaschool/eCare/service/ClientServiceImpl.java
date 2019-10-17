package tsystems.javaschool.eCare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tsystems.javaschool.eCare.dao.ClientDAO;
import tsystems.javaschool.eCare.dao.RoleDAO;
import tsystems.javaschool.eCare.model.Client;
import tsystems.javaschool.eCare.model.Role;

import java.util.HashSet;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

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
    public void add(Client client) {
        client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDAO.getById(1L));
        client.setRoles(roles);
        clientDAO.add(client);
    }

    @Override
    @Transactional
    public Client getById(Long id) {
         return clientDAO.getById(id);
    }

    @Override
    @Transactional
    public Client findByUsername(String username) {
        return clientDAO.findByUsername(username);
    }
}