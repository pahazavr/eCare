package tsystems.javaschool.eCare.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tsystems.javaschool.eCare.ECareException;
import tsystems.javaschool.eCare.dao.OptionDAO;
import tsystems.javaschool.eCare.dao.TariffDAO;
import tsystems.javaschool.eCare.model.Option;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OptionServiceImpl implements OptionService {

    private static Logger logger = Logger.getLogger(OptionService.class);

    private OptionDAO optionDAO;

    @Autowired
    public void setOptionDAO(OptionDAO optionDAO) {
        this.optionDAO = optionDAO;
    }

    private TariffDAO tariffDAO;
    @Autowired
    public void setTariffDAO(TariffDAO tariffDAO) {
        this.tariffDAO = tariffDAO;
    }

    @Override
    @Transactional
    public void add(Option option) throws ECareException {
        logger.info("Add option " + option + " in DB.");
        try {
            optionDAO.add(option);
            logger.info("Option " + option + " added in DB.");
        } catch (Exception ex) {
            ECareException eCareException = new ECareException("Failed to add option " + option + " in DB.", ex);
            logger.warn(eCareException.getMessage(), ex);
            throw eCareException;
        }
    }

    @Override
    @Transactional
    public void edit(Option option) throws ECareException {
        logger.info("Update option " + option + " in DB.");
        try {
            optionDAO.edit(option);
            logger.info("Option " + option + " updated in DB.");
        } catch (Exception ex) {
            ECareException eCareException = new ECareException("Failed to update option " + option + " in DB.", ex);
            logger.warn(eCareException.getMessage(), ex);
            throw eCareException;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ECareException {
        logger.info("Delete option with id: " + id + " from DB.");
        Option option = optionDAO.getOptionById(id);
        //If DAO returns null method will throws an ECareException
        if (option == null) {
            ECareException ecx = new ECareException("Option with id = " + id + " not exist.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        // Else option will be deleted from the database.
        optionDAO.delete(option);
        logger.info("Option " + option + " deleted from DB.");
    }

    @Override
    @Transactional
    public Option getOptionById(Long id) throws ECareException {
        logger.info("Load option with id: " + id + " from DB.");
        Option option = optionDAO.getOptionById(id);
        //If DAO returns null method will throws an ECareException
        if (option == null) {
            ECareException ecx = new ECareException("Option with id = " + id + " not found in DB.");
            logger.warn(String.valueOf(ecx), ecx);
            throw ecx;
        }
        logger.info("Option " + option + " loaded from DB.");
        //else method returns option entity
        return option;
    }

    @Override
    @Transactional
    public List<Option> getAllOptions() {
        return null;
    }

    @Override
    @Transactional
    public void deleteAllOptions() {

    }

    @Override
    @Transactional
    public Long getNumberOfOptions() {
        logger.info("Get number of options in DB.");
        Long number = optionDAO.getSize();
        logger.info(number + "of options obtained from DB.");
        return number;
    }

    @Override
    @Transactional
    public List<Option> getAllOptionsForTariff(Long id) throws ECareException {
        logger.info("Get all options from DB for tariff with id: " + id + ".");
        List<Option> options = optionDAO.getAllOptionsForTariff(id);
        //If DAO returns null method will throws an ECareException
        if (options == null) {
            ECareException ecx = new ECareException("Failed to get all options from DB for tariff id: " + id + ".");
            logger.error(ecx.getMessage(), ecx);
            throw ecx;
        }
        logger.info("All options for tariff id: " + id + " obtained from DB.");
        // Else method returns list of option entities
        return options;
    }

    @Override
    @Transactional
    public void deleteAllOptionsForTariff(Long id) {
        logger.info("Delete all options from DB for tariff with id: " + id + ".");
        optionDAO.deleteAllOptionsForTariff(id);
        logger.info("All options for tariff id: " + id + " deleted from DB.");
    }
}
