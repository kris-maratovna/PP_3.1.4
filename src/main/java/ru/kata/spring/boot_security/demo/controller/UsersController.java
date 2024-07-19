package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/")
public class UsersController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAll (@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.getByUsername(userDetails.getUsername());
        model.addAttribute("loggingUser", user);
        model.addAttribute("users", userService.showAll());
        model.addAttribute("user", new User());
        model.addAttribute("roleList", roleService.findAll());
        model.addAttribute("modifyRoles", user.getNameRole());
        return "/admin/index";
    }

    @GetMapping("/admin/addUser")
    public String addUser (Model model) {
        model.addAttribute("user", new User());
        return "admin/addUser";
    }
    @PostMapping("/admin/")
    public String create (@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/index";
        }
        userService.add(user);
        return "redirect:/admin";
    }


    @PostMapping("/admin/edit")
    public String editUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/index";
        }
        userService.edit(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String showUserInfo(@AuthenticationPrincipal UserDetails userDetails, Model model) throws UsernameNotFoundException {
        User user = userService.getByUsername(userDetails.getUsername());
        model.addAttribute("loggingUser", user);
            return "user";

    }

}
