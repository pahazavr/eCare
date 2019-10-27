package tsystems.javaschool.eCare.dao;

import tsystems.javaschool.eCare.model.Tariff;

import java.util.List;

public interface TariffDAO {
    void add(Tariff tariff);
    void edit(Tariff tariff);
    Tariff getTariffById(Long id);
    void delete(Tariff tariff);
    List<Tariff> getAllTariffs();
    void deleteAllTariffs();
    Long getSize();
}
