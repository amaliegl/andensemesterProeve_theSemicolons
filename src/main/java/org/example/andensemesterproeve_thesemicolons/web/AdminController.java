package org.example.andensemesterproeve_thesemicolons.web;

import jakarta.servlet.http.HttpSession;
import org.example.andensemesterproeve_thesemicolons.application.UserService;
import org.example.andensemesterproeve_thesemicolons.domain.Title_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAdminPage(HttpSession session){
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser.getTitle() == Title_ENUM.Admin) {
            return "admin/adminPage";
        }
        return "redirect:/homePage";
    }

    @GetMapping("/allUsers")
    public String getAllUsers(@RequestParam(required = false) String selectedTitleSort,
                              HttpSession session, Model model){
        User sessionUser = (User) session.getAttribute("currentUser");

        if (sessionUser.getTitle() == Title_ENUM.Admin) {
            model.addAttribute("titles", Title_ENUM.values());
            model.addAttribute("selectedTitleSort", selectedTitleSort);

            if (selectedTitleSort != null) {
                if (!selectedTitleSort.equals("Intet filter")) {
                    model.addAttribute("allUsers", userService.filterUsersByTitle(selectedTitleSort));
                    return "admin/allUsers";
                }
            }

            model.addAttribute("allUsers", userService.getAllUsers());
            return "admin/allUsers";
        }
        return "redirect:/homePage";
    }

    
}
