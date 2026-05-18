package org.example.andensemesterproeve_thesemicolons.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/homePage")
    public String getHomePage(HttpSession session, Model model){
        model.addAttribute("sessionUser", session.getAttribute("currentUser"));
        return "homePage";
    }

}
