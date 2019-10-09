package tsystems.javaschool.eCare.service;

import tsystems.javaschool.eCare.model.Tariff;

import java.util.List;

public interface TariffService {
    List<Tariff> allTariffs();
    void add(Tariff tariff);
    void edit(Tariff tariff);
    void delete(Tariff tariff);
    Tariff getById(int id);
}
