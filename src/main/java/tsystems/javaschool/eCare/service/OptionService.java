package tsystems.javaschool.eCare.service;

import tsystems.javaschool.eCare.model.Option;

import java.util.List;

public interface OptionService {
    void add(Option option);
    void edit(Option option);
    Option getOptionById(Long id);
    void delete(Long id);
    List<Option> getAllOptions();
    void deleteAllOptions();
    Long getNumberOfOptions();
    List<Option> getAllOptionsForTariff(Long id);
    void deleteAllOptionsForTariff(Long id);
}
