package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import web.models.Role;
import web.models.User;
import web.repositories.RoleRepositories;
import web.repositories.UserRepositories;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Repository
@EnableTransactionManagement
public class UserDaoImpl implements UserDao {
    private final UserRepositories userRepositories;
    private final RoleRepositories roleRepositories;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserDaoImpl(UserRepositories userRepositories, RoleRepositories roleRepositories) {
        this.userRepositories = userRepositories;
        this.roleRepositories = roleRepositories;
    }

    @Override
    @Transactional
    public Set<User> getAllUsers() {
        return new HashSet<>(entityManager.createQuery("select u from User u JOIN FETCH u.roles", User.class).getResultList());
    }

    @Override
    @Transactional
    public Role getRoleByLogin(String login) {
        User user = findUserByLogin(login);
        Query query = entityManager.createQuery("select r from Role r join fetch r.users where r.id = :id", Role.class);
        query.setParameter("id", user.getId());
        return (Role) query.getSingleResult();
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userRepositories.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        User u = findUserById(id);
        userRepositories.delete(u);
    }

    @Override
    @Transactional
    public User findUserById(Long id) {
        return userRepositories.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByLogin(String login) {
        Query query = entityManager.createQuery("select u from User u JOIN FETCH u.roles where u.login = :login", User.class);
                query.setParameter("login", login);
        User result = null;
        try {
            result = (User) query.getSingleResult();
        } catch (NoResultException e) {
            e.getStackTrace();
        }
        return result;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setLogin(user.getLastName());
        user.setRoles(Collections.singleton(new Role("ROLE_USER")));
        user.setPassword("100");
        System.out.println(user);
        userRepositories.save(user);
    }

}
