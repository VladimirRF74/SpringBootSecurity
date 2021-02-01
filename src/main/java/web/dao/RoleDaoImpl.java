package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import web.models.Role;
import web.repositories.RoleRepositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

@Repository
@EnableTransactionManagement
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;
    private final RoleRepositories roleRepositories;

    public RoleDaoImpl(RoleRepositories roleRepositories) {
        this.roleRepositories = roleRepositories;
    }

    @Override
    @Transactional
    public Set<Role> getAllRoles() {
        return new HashSet<>(entityManager.createQuery("select u from Role u ", Role.class).getResultList());
    }

    @Override
    @Transactional
    public Role getId(Long id) {
        return roleRepositories.findById(id).orElse(new Role());
    }

    //@Override
    //@Transactional
    //public Role getRole(String nameRole) {
    //    return entityManager.find(Role.class, nameRole);
    //}
    @Override
    @Transactional
    public String getRoleById(Long id) {
        Role r = roleRepositories.findById(id).orElse(new Role());
        return r.getNameRole();
    }
}
