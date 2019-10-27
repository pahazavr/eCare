package tsystems.javaschool.eCare.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tsystems.javaschool.eCare.model.Client;

import java.util.List;

@Repository
public class ClientDAOImpl implements ClientDAO {

    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /*
    @Override
    protected Client doSaveOrUpdate(Client client) {
        Session session = sessionFactory.getCurrentSession();
        return (Client) session.merge(client);
    }

    @Override
    protected Client doGetById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Client.class, id);
    }

    @Override
    protected void doDelete(Client client) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(client);
    }

    @Override
    protected List<Client> doGetAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createNamedQuery("Client.getAllClients", Client.class).getResultList();
    }

    @Override
    protected void doDeleteAll() {
        Session session = sessionFactory.getCurrentSession();
        session.createNamedQuery("Client.deleteAllClients").executeUpdate();
    }

    @Override
    protected Long doSize() {
        Session session = sessionFactory.getCurrentSession();
        return (Long)session.createNamedQuery("Client.size").getSingleResult();
    }
    */

    @Override
    public void add(Client client) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(client);
    }

    @Override
    public void edit(Client client) {
        Session session = sessionFactory.getCurrentSession();
        session.update(client);
    }

    @Override
    public void delete(Client client) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(client);
    }

    @Override
    public List<Client> getAllClients() {
        Session session = sessionFactory.getCurrentSession();
        return session.createNamedQuery("Client.getAllClients", Client.class).getResultList();
    }

    @Override
    public void deleteAllClients() {
        Session session = sessionFactory.getCurrentSession();
        session.createNamedQuery("Client.deleteAllClients").executeUpdate();
    }

    @Override
    public Long getSize() {
        Session session = sessionFactory.getCurrentSession();
        return (Long)session.createNamedQuery("Client.size").getSingleResult();
    }

    @Override
    public Client getClientById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Client.class, id);
    }

    @Override
    public Client findClientByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM tsystems.javaschool.eCare.model.Client WHERE email = ?");
        query.setParameter(0, email);
        return (Client) query.getSingleResult();
    }

    @Override
    public Client findClientByLoginAndPassword(String login, String password) {
        Session session = sessionFactory.getCurrentSession();
        return session.createNamedQuery("Client.findClientByLoginAndPassword", Client.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .getSingleResult();
    }

    @Override
    public Client findClientByNumber(Long number) {
        Session session = sessionFactory.getCurrentSession();
        return session.createNamedQuery("Client.findClientByNumber", Client.class)
                .setParameter("number", number)
                .getSingleResult();
    }


}
