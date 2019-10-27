package tsystems.javaschool.eCare.service;

import tsystems.javaschool.eCare.model.Tariff;

import java.util.List;

public interface TariffService {
    List<Tariff> getAllTariffs();
    void add(Tariff tariff);
    void edit(Tariff tariff);
    void delete(Long id);
    Tariff getTariffById(Long id);
    void deleteAllTariffs();
    Long getNumberOfTariffs();
}
