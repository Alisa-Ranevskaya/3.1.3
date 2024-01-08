package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @PutMapping("/users/{id}")
    default ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User updatedUser,
                                            @RequestParam("authorities") Set<String> values) {
        User user = this.getUserById(id);
        if (user != null) {
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setAge(updatedUser.getAge());
            user.setLogin(updatedUser.getLogin());
            user.setPassword(updatedUser.getPassword());
            Set<Role> roles = values.stream()
                    .map(roleName -> {
                        Role role = new Role();
                        role.setRole(roleName);
                        return role;
                    })
                    .collect(Collectors.toSet());
            user.setRoles(roles);
            this.updateUser(id, user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{id}")
    default ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
        User user = this.getUserById(id);
        if (user != null) {
            this.removeUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}