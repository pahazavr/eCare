package tsystems.javaschool.eCare.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tsystems.javaschool.eCare.model.Option;

import javax.persistence.Query;
import java.util.List;

@Repository
public class OptionDAOImpl implements OptionDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Option option) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(option);
    }

    @Override
    public void edit(Option option) {
        Session session = sessionFactory.getCurrentSession();
        session.update(option);
    }

    @Override
    public void delete(Option option) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(option);
    }

    @Override
    public List<Option> getAllOptions() {
        Session session = sessionFactory.getCurrentSession();
        return session.createNamedQuery("Option.getAllOptions", Option.class).getResultList();
    }

    @Override
    public void deleteAllOptions() {
        Session session = sessionFactory.getCurrentSession();
        session.createNamedQuery("Option.deleteAllOptions").executeUpdate();
    }

    @Override
    public Long getSize() {
        Session session = sessionFactory.getCurrentSession();
        return (Long)session.createNamedQuery("Option.size").getSingleResult();
    }

    @Override
    public Option getOptionById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Option.class, id);
    }

    @Override
    public List<Option> getAllOptionsForTariff(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createNamedQuery("Option.getAllOptionsForTariff", Option.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public void deleteAllOptionsForTariff(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.createNamedQuery("Option.deleteAllOptionsForTariff")
                .setParameter(1, id)
                .executeUpdate();
    }

    @Override
    public Option findOptionByTitleAndTariffId(String title, Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createNamedQuery("Option.findOptionByTitleAndTariffId", Option.class)
                .setParameter("title", title)
                .setParameter("id", id)
                .getSingleResult();
    }
}
