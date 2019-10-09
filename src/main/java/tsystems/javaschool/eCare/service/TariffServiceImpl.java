package tsystems.javaschool.eCare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tsystems.javaschool.eCare.dao.TariffDAO;
import tsystems.javaschool.eCare.model.Tariff;

import java.util.List;

@Service
public class TariffServiceImpl implements TariffService {

    private TariffDAO tariffDAO;

    @Autowired
    public void setTariffDAO(TariffDAO tariffDAO) {
        this.tariffDAO = tariffDAO;
    }

    @Override
    @Transactional
    public List<Tariff> allTariffs() {
        return tariffDAO.allTariffs();
    }

    @Override
    @Transactional
    public void add(Tariff tariff) {
        tariffDAO.add(tariff);
    }

    @Override
    @Transactional
    public void edit(Tariff tariff) {
        tariffDAO.edit(tariff);
    }

    @Override
    @Transactional
    public void delete(Tariff tariff) {
        tariffDAO.delete(tariff);
    }

    @Override
    @Transactional
    public Tariff getById(int id) {
        return tariffDAO.getById(id);
    }
}
