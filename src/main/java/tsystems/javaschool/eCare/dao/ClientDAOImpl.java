package tsystems.javaschool.eCare.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
    public Client getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Client.class, id);
    }

    @Override
    public Client findByUsername(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query<Client> q = session.createQuery("from Client where email=:email", Client.class)
                .setParameter("email", email);
        if(q.list().size()>0){
            return q.getSingleResult();
        } else {
            return null;
        }

    }
}
