package tsystems.javaschool.eCare.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tsystems.javaschool.eCare.model.Client;

@Repository
public class ClientDAOImpl implements ClientDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Client client) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(client);
    }

    @Override
    public Client getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Client.class, id);
    }
}
