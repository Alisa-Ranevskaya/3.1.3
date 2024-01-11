package ru.itmentor.spring.boot_security.demo.service;


import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    void saveUser(User user);
    void removeUserById(long id);
    User getUserById(long id);
    Role getRoleById(long id);
    List<User> getUsers();
    Set<Role> getRoles();
    Set<Role> getRole(Set<String> roleId);
    void updateUser(long id, User updatedUser);
    User findByLogin(String login);

}