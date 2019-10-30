package tsystems.javaschool.eCare.service;

import tsystems.javaschool.eCare.model.Contract;
import tsystems.javaschool.eCare.model.Option;
import tsystems.javaschool.eCare.model.Tariff;

import java.util.List;

public interface ContractService {
    List<Contract> getAllContracts();
    void add(Contract contract);
    void edit(Contract contract);
    void delete(Long id);
    Contract getContractById(Long id);
    Contract findContractByNumber(Long number);

    /**
     * Получает список контрактов для данного клиента
     * @param id clientId.
     * @return List of received contracts.
     */
    List<Contract> getAllContractsForClient(Long id);
    void deleteAllContracts();
    void deleteAllContractsForClient(Long id);
    Long getNumberOfContracts();
    boolean isBlockedByClient(Contract contract);
    boolean isBlockedByOperator(Contract contract);
    void blockByClient(Contract contract);
    void unblockByClient(Contract contract);
    void blockByOperator(Contract contract);
    void unblockByOperator(Contract contract);
    Contract enableOption(Contract cn, Option op);
    Contract disableOption(Contract cn, Option op);
    Contract setTariff(Contract contract, Tariff tariff, String chosenOptionsArray[]);
    Contract setDefaultTariff(Contract contract);
}
