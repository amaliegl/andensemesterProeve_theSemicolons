package org.example.andensemesterproeve_thesemicolons.web;

import jakarta.servlet.http.HttpSession;
import org.example.andensemesterproeve_thesemicolons.application.UserService;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login2")
    public String getLoginPage() {
        return "authentication/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "authentication/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session, Model model) {
        User loggedIn = userService.login(user.getUsername(), user.getPassword());

        if (loggedIn != null) {
            session.setAttribute("currentUser", loggedIn);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Forkert brugernavn eller adgangskode.");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "authentication/register";
    }
}
