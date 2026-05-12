package org.example.andensemesterproeve_thesemicolons.web;

import jakarta.servlet.http.HttpSession;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "authentication/login";
    }

    @GetMapping("/login2")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "authentication/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session, Model model) {
        return "login";
    }

    @GetMapping("/registrer")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "authentication/register";
    }
}
