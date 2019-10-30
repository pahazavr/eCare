package tsystems.javaschool.eCare.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import tsystems.javaschool.eCare.model.Client;
import tsystems.javaschool.eCare.service.ClientService;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class ClientValidator implements Validator {

    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Client.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Client client = (Client) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");
        if (client.getName().length() > 60) {
            errors.rejectValue("name", "Size.client.name");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "Required");
        if (client.getName().length() > 60) {
            errors.rejectValue("surname", "Size.client.surname");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate", "Required");
        try {
            Date date = client.getBirthDate();
        }
        catch (Exception ex) {
            errors.rejectValue("birthDate", "Convert.client.birthDate");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "Required");
        if (client.getAddress().length() > 60) {
            errors.rejectValue("surname", "Size.client.address");
        }

        if (client.getEmail().length() < 8 || client.getEmail().length() > 32) {
            errors.rejectValue("email", "Size.client.email");
        }

//        if (clientService.findClientByEmail(client.getEmail()) != null) {
//            errors.rejectValue("email", "Duplicate.client.email");
//        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if (client.getPassword().length() < 8 || client.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.client.password");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "Required");
        if (!client.getConfirmPassword().equals(client.getPassword())) {
            errors.rejectValue("confirmPassword", "Different.client.password");
        }
    }
}
