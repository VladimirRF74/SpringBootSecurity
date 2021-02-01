package web.dao;

import web.models.Role;

import java.util.Set;

public interface RoleDao {
    Role getId(Long id);
    //Role getRole(String nameRole);
    Set<Role> getAllRoles();
    String getRoleById(Long id);
}
