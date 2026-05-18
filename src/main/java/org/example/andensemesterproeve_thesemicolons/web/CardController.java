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
    public String getMyCards(@RequestParam(required = false) String selectedSetSort, HttpSession session, Model model){


        model.addAttribute("sets", cardService.getAllSets());
        model.addAttribute("types", cardService.getAllTypes());
        model.addAttribute("selectedSetSort", selectedSetSort);

        if (selectedSetSort == null) {
            System.out.println("Er i selectedSetSort==null");
            User sessionUser = (User) session.getAttribute("currentUser");
            model.addAttribute("cards", sessionUser.getCards());
            return "/cards/myCards";
        }
        System.out.println("selectedSetSort = " + selectedSetSort);

        if (selectedSetSort.equals("Intet filter")) {
            System.out.println("Er i selectedSetSort.equals(intet filter)");
            User sessionUser = (User) session.getAttribute("currentUser");
            model.addAttribute("cards", sessionUser.getCards());
            return "/cards/myCards";
        }

        /*
        User sessionUser = (User) session.getAttribute("currentUser");
        List<Card> testCards = sessionUser.getCards();
        List<Card> randomCards = new ArrayList<>();
        randomCards.add(testCards.get(1));
        randomCards.add(testCards.get(2));
        model.addAttribute("cards", testCards);
        */

        model.addAttribute("cards", new ArrayList<>());
        return "/cards/myCards";


    }

/*
    @GetMapping("/myCards")
    public String getMyCards(HttpSession session, Model model){
        User sessionUser = (User) session.getAttribute("currentUser");
        model.addAttribute("cards", sessionUser.getCards());
        model.addAttribute("sets", cardService.getAllSets());
        model.addAttribute("types", cardService.getAllTypes());
        return "/cards/myCards";
    }
    */

/*
    @GetMapping("/myCards/set/{set}")
    public String getMyCards(@PathVariable String set, HttpSession session, Model model){
        System.out.println("Settet er: " + set);
        User sessionUser = (User) session.getAttribute("currentUser");
        model.addAttribute("cards", sessionUser.getCards());
        model.addAttribute("sets", cardService.getAllSets());
        model.addAttribute("types", cardService.getAllTypes());
        return "/cards/myCards";
    }
    */

}
