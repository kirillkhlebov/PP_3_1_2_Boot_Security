package ru.kata.SpirngSecurityApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.SpirngSecurityApp.models.User;
import ru.kata.SpirngSecurityApp.services.RoleServiceInt;
import ru.kata.SpirngSecurityApp.services.UserServiceInt;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceInt userService;
    private final RoleServiceInt roleService;

    @Autowired
    public AdminController(UserServiceInt userService, RoleServiceInt roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showUsers(Model model) {
        List<User> listOfUsers = userService.findAll();
        model.addAttribute("users", listOfUsers);
        return "admin/admin";
    }

    @GetMapping("/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "admin/new";
    }


    @PostMapping("/new")
    public String createUser(@ModelAttribute User user, Model model) {
        if (userService.isExist(user.getEmail())) {
            model.addAttribute("error", "User with this email already exists");
            model.addAttribute("roles", roleService.findAll());

            return "admin/new";
        }
        userService.saveAndFlush(user);
        return "redirect:/admin/admin";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, @ModelAttribute User user, Model model) {
        Optional<User> userOptional = userService.findById(id);
        model.addAttribute("user", userOptional.get());
        model.addAttribute("roles", roleService.findAll());
        return "admin/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable long id, @ModelAttribute("user") User updatedUser, Model model) {
        if (userService.isEmailCanNotBeChanged(updatedUser, id)) {
            model.addAttribute("error", "User with this email already exists");
            model.addAttribute("user", updatedUser);
            model.addAttribute("roles", roleService.findAll());

            return "admin/edit";
        }
        userService.save(updatedUser);
        return "redirect:/admin/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        userService.delete(userOptional.get());
        return "redirect:/admin/admin";
    }

}
