package tsystems.javaschool.eCare.dao;

import tsystems.javaschool.eCare.model.Client;
import tsystems.javaschool.eCare.model.Role;

import java.util.List;

public interface RoleDAO {
    List<Role> allRoles();
    Role getById(Long id);
}
