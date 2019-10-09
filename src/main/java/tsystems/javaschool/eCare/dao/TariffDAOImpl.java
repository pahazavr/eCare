package tsystems.javaschool.eCare.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tsystems.javaschool.eCare.model.Tariff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TariffDAOImpl implements TariffDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tariff> allTariffs() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Tariff").list();
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
    public Tariff getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Tariff.class, id);
    }
}
