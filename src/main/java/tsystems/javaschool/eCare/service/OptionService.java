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

    Option setDependentOption(Option currentOption, Option dependentOption);
    Option deleteDependentOption(Option currentOption, Option dependentOption);
    void clearDependentOptions(Option currentOption);
    Option setIncompatibleOption(Option currentOption, Option incompatibleOption);
    Option deleteIncompatibleOption(Option currentOption, Option incompatibleOption);
    void clearIncompatibleOptions(Option currentOption);
    Option createDependencies(Option option, String[] dependentOptionsArray, String[] incompatibleOptionsArray);
}
