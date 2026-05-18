package org.example.andensemesterproeve_thesemicolons.web;

import jakarta.servlet.http.HttpSession;
import org.example.andensemesterproeve_thesemicolons.domain.Title_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String getMyDeck(HttpSession session){
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser.getTitle() == Title_ENUM.Admin) {
            return "admin/adminPage";
        }
        return "redirect:/homePage";
    }
}
