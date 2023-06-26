package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import app.model.User;
import app.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public String getAllUsers(Model model) {
        try {
            List<User> getAllUsers = userService.getAllUsers();
            model.addAttribute("getAllUsers", getAllUsers);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "users";
    }

    @GetMapping("/create")
    public String createUserForm(@ModelAttribute("user")User user) {
        return "create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user")User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}")
    public String getUser(Model model, @PathVariable("id") Long id) {
        try {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "get_delete";
    }

    @GetMapping("/delete/{id}")
    public String deleteUserForm(Model model, @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("allowDelete", true);
        model.addAttribute("user", user);
        return "get_delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(Model model, @PathVariable("id") Long id) {
        try {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/users";
    }
}