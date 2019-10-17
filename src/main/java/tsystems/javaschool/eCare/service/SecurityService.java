package tsystems.javaschool.eCare.service;

public interface SecurityService {

    String findLoggedInUsername();
    void autoLogin(String username, String password);
}
