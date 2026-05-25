package org.example.andensemesterproeve_thesemicolons.web;

import jakarta.servlet.http.HttpSession;
import org.example.andensemesterproeve_thesemicolons.application.UserService;
import org.example.andensemesterproeve_thesemicolons.domain.enums.Title_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAdminPage(HttpSession session){
        User sessionUser = (User) session.getAttribute("currentUser");
        if (userService.confirmUserIsAdmin(sessionUser)) {
            return "admin/adminPage";
        }
        return "redirect:/homePage";
    }

    @GetMapping("/allUsers")
    public String getAllUsers(@RequestParam(required = false) String selectedTitleSort,
                              HttpSession session, Model model){
        User sessionUser = (User) session.getAttribute("currentUser");

        if (userService.confirmUserIsAdmin(sessionUser)) {
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

    @GetMapping("/allUsers/editUser/{username}")
    public String getEditUserPage(@PathVariable String username,
                                  HttpSession session, Model model){
        User sessionUser = (User) session.getAttribute("currentUser");

        if (userService.confirmUserIsAdmin(sessionUser)) {
            User user = userService.adminFindUserByUsername(sessionUser, username);
            if (user == null) {
                return "redirect:/admin/allUsers";
            }
            model.addAttribute("userToEdit", user);
            return "admin/editUser";
        }
        return "redirect:/homePage";
    }

    @PostMapping("/allUsers/editUser")
    public String submitEditUserForm(@ModelAttribute User userToEdit, HttpSession session){
        User sessionUser = (User) session.getAttribute("currentUser");

        if (userService.confirmUserIsAdmin(sessionUser)) {
            userService.adminEditUser(sessionUser, userToEdit);
            return "redirect:/admin/allUsers";
        }
        return "redirect:/homePage";
    }
}