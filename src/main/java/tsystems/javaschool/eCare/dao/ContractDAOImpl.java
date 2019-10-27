package tsystems.javaschool.eCare.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tsystems.javaschool.eCare.model.Contract;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ContractDAOImpl implements ContractDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Contract contract) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(contract);
    }

    @Override
    public void edit(Contract contract) {
        Session session = sessionFactory.getCurrentSession();
        session.update(contract);
    }

    @Override
    public void delete(Contract contract) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(contract);
    }

    @Override
    public List<Contract> getAllContracts() {
        Session session = sessionFactory.getCurrentSession();
        return session.createNamedQuery("Contract.getAllContracts", Contract.class).getResultList();
    }

    @Override
    public void deleteAllContracts() {
        Session session = sessionFactory.getCurrentSession();
        session.createNamedQuery("Contract.deleteAllContracts").executeUpdate();
    }

    @Override
    public Long getSize() {
        Session session = sessionFactory.getCurrentSession();
        return (Long)session.createNamedQuery("Contract.size").getSingleResult();
    }

    @Override
    public Contract getContractById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Contract.class, id);
    }

    @Override
    public Contract findContractByNumber(Long number) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNamedQuery("Contract.findContractByNumber", Contract.class)
                .setParameter("number", number);
        return (Contract) query.getSingleResult();
    }

    @Override
    public List<Contract> getAllContractsForClient(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNamedQuery("Contract.getAllContractsForClient", Contract.class)
                .setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public void deleteAllContractsForClient(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNamedQuery("Contract.deleteAllContractsForClient")
                .setParameter(1, id);
        query.executeUpdate();
    }
}
