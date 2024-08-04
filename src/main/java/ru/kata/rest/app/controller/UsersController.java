package ru.kata.rest.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.rest.app.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UsersController {

    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String showIndexPageAdmin(HttpServletResponse response) {
        createCookie(response);
        return "index2";
    }

    @GetMapping("/user")
    public String showIndexPageUser(HttpServletResponse response) {
        createCookie(response);
        return "userHome";
    }

    private void createCookie(HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        long id = userService.getByUsername(auth.getName()).getId();
        Cookie currentUserCookie = new Cookie("UserId", "" + id);
        currentUserCookie.setMaxAge(60 * 60);
        response.addCookie(currentUserCookie);
        boolean isAdmin = userService.isAdmin(auth.getName());
        Cookie checkAdminCookie = new Cookie("Admin", "" + isAdmin);
        currentUserCookie.setMaxAge(60 * 60);
        response.addCookie(checkAdminCookie);
    }
}
