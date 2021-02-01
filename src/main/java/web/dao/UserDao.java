package web.dao;

import web.models.Role;
import web.models.User;

import java.util.Set;

public interface UserDao {
    void addUser(User user);
    Set<User> getAllUsers();
    void updateUser(User user);
    void deleteUserById(Long id);
    User findUserById(Long id);
    User findUserByLogin(String login);
    Role getRoleByLogin(String login);
}
