package tsystems.javaschool.eCare.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import tsystems.javaschool.eCare.model.Client;
import tsystems.javaschool.eCare.service.ClientService;

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

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Required");
        if (client.getEmail().length() < 3 || client.getEmail().length() > 32) {
            errors.rejectValue("email", "Size.client.email");
        }

        if (clientService.findByUsername(client.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.client.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if (client.getPassword().length() < 3 || client.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.client.password");
        }

        if (!client.getConfirmPassword().equals(client.getPassword())) {
            errors.rejectValue("confirmPassword", "Different.client.password");
        }
    }
}
