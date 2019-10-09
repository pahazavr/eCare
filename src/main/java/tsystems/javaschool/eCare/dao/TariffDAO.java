package tsystems.javaschool.eCare.dao;

import tsystems.javaschool.eCare.model.Tariff;

import java.util.List;

public interface TariffDAO {
    List<Tariff> allTariffs();
    void add(Tariff tariff);
    void edit(Tariff tariff);
    void delete(Tariff tariff);
    Tariff getById(int id);
}
