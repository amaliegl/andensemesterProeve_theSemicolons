package org.example.andensemesterproeve_thesemicolons.web;

import jakarta.servlet.http.HttpSession;
import org.example.andensemesterproeve_thesemicolons.application.CardService;
import org.example.andensemesterproeve_thesemicolons.domain.Card;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/myCards")
    public String getMyCards(@RequestParam(required = false) String selectedSetSort,
                             @RequestParam(required = false) String selectedTypeSort,
                             HttpSession session, Model model){

        model.addAttribute("sets", cardService.getAllSets());
        model.addAttribute("types", cardService.getAllTypes());
        model.addAttribute("selectedSetSort", selectedSetSort);
        model.addAttribute("selectedTypeSort", selectedTypeSort);
        User sessionUser = (User) session.getAttribute("currentUser");

        if (selectedSetSort != null && selectedTypeSort != null) {
            if (selectedSetSort.equals("Intet filter") && selectedTypeSort.equals("Intet filter")) {
                model.addAttribute("cards", sessionUser.getCards());
                return "/cards/myCards";
            }

            if (selectedTypeSort.equals("Intet filter")) {
                model.addAttribute("cards", cardService.filterUserCardsBySet(sessionUser, selectedSetSort));
                return "/cards/myCards";
            }

            if (selectedSetSort.equals("Intet filter")) {
                model.addAttribute("cards", cardService.filterUserCardsByType(sessionUser, selectedTypeSort));
                return "/cards/myCards";
            }

            model.addAttribute("cards", cardService.filterUserCardsBySetAndType(sessionUser, selectedSetSort, selectedTypeSort));
            return "/cards/myCards";
        }

        model.addAttribute("cards", sessionUser.getCards());
        return "/cards/myCards";
    }
}
