package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(ModelMap model) {
        List<User> users = userService.getUsers();
        Set<Role> roles = userService.getRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/admin/addUser")
    public String createUserForm(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("roles", userService.getRoles());
        return "add-user-form";
    }

    @PostMapping("/admin/addUser")
    public String saveUser(@ModelAttribute("userForm") User userForm,
                           @RequestParam("authorities") Set<String> values) {
        userForm.setRoles(userService.getRole(values));
        userService.saveUser(userForm);
        return "redirect:/admin";
    }

    @GetMapping("/admin/update/{id}")
    public String showFormForUpdate(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", userService.getRoles());
        return "update-user-form";
    }

    @PostMapping("/admin/update/{id}")
    public String updateUser(
            @PathVariable("id") Long id, @ModelAttribute("user") User user,
            @RequestParam("authorities") Set<String> values) {
        user.setRoles(userService.getRole(values));
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}