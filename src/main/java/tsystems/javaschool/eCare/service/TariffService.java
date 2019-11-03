package tsystems.javaschool.eCare.service;

import tsystems.javaschool.eCare.model.Tariff;

import java.util.List;

public interface TariffService {
    List<Tariff> getAllTariffs();
    void add(Tariff tariff);
    void edit(Tariff tariff);
    Tariff getTariffById(Long id);
    void deleteTariff(Long id);
    void deleteAllTariffs();
    Long getNumberOfTariffs();
}
