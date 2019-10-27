package tsystems.javaschool.eCare.service;

public interface SecurityService {

    String findLoggedInEmail();
    void autoLogin(String username, String password);
}
