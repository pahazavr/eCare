package tsystems.javaschool.eCare.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tsystems.javaschool.eCare.model.Tariff;

import javax.persistence.Query;
import java.util.List;

@Repository
public class TariffDAOImpl implements TariffDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Tariff tariff) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(tariff);
    }

    @Override
    public void edit(Tariff tariff) {
        Session session = sessionFactory.getCurrentSession();
        session.update(tariff);
    }

    @Override
    public void delete(Tariff tariff) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(tariff);
}

    @Override
    public List<Tariff> getAllTariffs() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNamedQuery("Tariff.getAllTariffs", Tariff.class);
        return (List<Tariff>) query.getResultList();

    }

    @Override
    public void deleteAllTariffs() {
        Session session = sessionFactory.getCurrentSession();
        session.createNamedQuery("Tariff.deleteAllTariffs").executeUpdate();
    }

    @Override
    public Long getSize() {
        Session session = sessionFactory.getCurrentSession();
        return (Long)session.createNamedQuery("Tariff.size").getSingleResult();
    }

    @Override
    public Tariff getTariffById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Tariff.class, id);
    }

}
