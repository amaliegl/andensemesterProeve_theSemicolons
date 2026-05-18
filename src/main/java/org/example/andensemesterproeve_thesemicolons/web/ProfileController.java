package org.example.andensemesterproeve_thesemicolons.web;

import jakarta.servlet.http.HttpSession;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/myProfile")
    public String GetMyProfile(Model model, HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        if (user == null){
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "/profile/myProfile";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }

}
