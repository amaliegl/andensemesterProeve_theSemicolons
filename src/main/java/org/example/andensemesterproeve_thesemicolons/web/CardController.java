package org.example.andensemesterproeve_thesemicolons.web;

import jakarta.servlet.http.HttpSession;
import org.example.andensemesterproeve_thesemicolons.application.CardService;
import org.example.andensemesterproeve_thesemicolons.application.UserService;
import org.example.andensemesterproeve_thesemicolons.domain.Card;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/myCards")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getMyCards(@RequestParam(required = false) String selectedSetSort,
                             @RequestParam(required = false) String selectedTypeSort,
                             HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("currentUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("sets", cardService.getAllSets());
        model.addAttribute("types", cardService.getAllTypes());
        model.addAttribute("selectedSetSort", selectedSetSort);
        model.addAttribute("selectedTypeSort", selectedTypeSort);
        model.addAttribute("user", sessionUser);

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

    @GetMapping("/search")
    public String getUserCardsFromSearch(@RequestParam String searchKeyword, HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("currentUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("cards", cardService.getAllUserCardsBySearchParam(sessionUser, searchKeyword));
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("user", sessionUser);
        return "/cards/myCards";
    }

    @GetMapping("/details/{id}")
    public String getCardDetailsPage(@PathVariable int id, HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("currentUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        Card card = cardService.getCardByOwnedCardId(id);

        if (card == null) {
            return "redirect:/myCards";
        }

        model.addAttribute("card", card);
        return "/cards/myCardDetails";
    }

    @PostMapping("/details")
    public String submitCardDetailsForm(@ModelAttribute Card card, HttpSession session) {
        User sessionUser = (User) session.getAttribute("currentUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        cardService.updateUserOwnedCard(card);
        refreshCurrentSessionUser(session);
        return "redirect:/myCards";
    }

    @GetMapping("/myCards/addCard")
    public String getAllCards(@RequestParam(required = false) String selectedSetSort,
                              @RequestParam(required = false) String selectedTypeSort,
                              HttpSession session, Model model) {

        User sessionUser = (User) session.getAttribute("currentUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("sets", cardService.getAllSets());
        model.addAttribute("types", cardService.getAllTypes());
        model.addAttribute("selectedSetSort", selectedSetSort);
        model.addAttribute("selectedTypeSort", selectedTypeSort);

        if (selectedSetSort != null && selectedTypeSort != null) {
            if (selectedSetSort.equals("Intet filter") && selectedTypeSort.equals("Intet filter")) {
                model.addAttribute("allCards", cardService.getAllCards());
                return "/cards/addCardToMyCards";
            }

            if (selectedTypeSort.equals("Intet filter")) {
                model.addAttribute("allCards", cardService.getAllCardsBySet(selectedSetSort));
                return "/cards/addCardToMyCards";
            }

            if (selectedSetSort.equals("Intet filter")) {
                model.addAttribute("allCards", cardService.getAllCardsByType(selectedTypeSort));
                return "/cards/addCardToMyCards";
            }

            model.addAttribute("allCards", cardService.getAllCardsBySetAndType(selectedSetSort, selectedTypeSort));
            return "/cards/addCardToMyCards";
        }

        model.addAttribute("allCards", cardService.getAllCards());
        return "/cards/addCardToMyCards";
    }


    @PostMapping("/myCards/addCard/{cardId}")
    public String addCardToUserCollection(@PathVariable int cardId,
                                          @RequestParam(required = false) String selectedSetSort,
                                          @RequestParam(required = false) String selectedTypeSort,
                                          HttpSession session,
                                          RedirectAttributes redirectAttributes) {
        User sessionUser = (User) session.getAttribute("currentUser");
        cardService.addCardToUsersCollection(cardId, sessionUser);

        if (selectedSetSort == null || selectedSetSort.isBlank()) {
            selectedSetSort = "Intet filter";
        }

        if (selectedTypeSort == null || selectedTypeSort.isBlank()) {
            selectedTypeSort = "Intet filter";
        }

        redirectAttributes.addAttribute("selectedSetSort", selectedSetSort);
        redirectAttributes.addAttribute("selectedTypeSort", selectedTypeSort);

        refreshCurrentSessionUser(session);

        return "redirect:/myCards/addCard";
    }

    private void refreshCurrentSessionUser(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        session.setAttribute("currentUser", userService.refreshSessionUser(user));
    }
}
