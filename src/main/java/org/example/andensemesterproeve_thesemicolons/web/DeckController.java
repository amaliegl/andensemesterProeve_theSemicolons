package org.example.andensemesterproeve_thesemicolons.web;

import jakarta.servlet.http.HttpSession;
import org.example.andensemesterproeve_thesemicolons.application.DeckService;
import org.example.andensemesterproeve_thesemicolons.application.UserService;
import org.example.andensemesterproeve_thesemicolons.domain.Deck;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/myDecks")
public class DeckController {

    private final DeckService deckService;
    private final UserService userService;

    public DeckController(DeckService deckService, UserService userService) {
        this.deckService = deckService;
        this.userService = userService;
    }

    //Main deck page, displaying all decks
    @GetMapping
    public String getMyDeckPage(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("currentUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("decks", sessionUser.getDecks());

        return "/deck/myDecks";
    }

    //Creating a new Deck
    @GetMapping("addDeck")
    public String getNewDeckPage(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("currentUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("deck", new Deck());
        return "deck/newDeck";
    }

    @PostMapping("addDeck")
    public String postNewDeckForm(@ModelAttribute Deck deck, HttpSession session) {
        User sessionUser = (User) session.getAttribute("currentUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        deckService.createNewUserDeck(sessionUser, deck);
        refreshCurrentSessionUser(session);
        return "redirect:/myDecks";
    }

    //Showing a specific deck
    @GetMapping("/details/{id}")
    public String showSpecificDeckPage(@PathVariable int id, HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("currentUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        Deck deck = deckService.getDeckById(id, sessionUser);

        if (deck == null) {
            return "redirect:/myDecks";
        }

        model.addAttribute("deck", deck);
        return "/deck/details";
    }

    //Edit specific deck
    @GetMapping("/editDeck/{id}")
    public String getEditDeckPage(@PathVariable int id, HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("currentUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        Deck deck = deckService.getDeckById(id, sessionUser);

        if (deck == null) {
            return "redirect:/myDecks";
        }

        model.addAttribute("deck", deck);
        return "/deck/editDeck";
    }

    @PostMapping("/editDeck")
    public String submitEditDeckForm(@ModelAttribute Deck deck, HttpSession session) {
        User sessionUser = (User) session.getAttribute("currentUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        deckService.editUserDeckInfo(sessionUser, deck);

        refreshCurrentSessionUser(session);
        return "redirect:/myDecks/details/" + deck.getId();
    }

    //Page for adding card to deck
    @GetMapping("/details/{id}/addCard")
    public String getAddCardToDeckPage(@PathVariable int id, HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("currentUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        Deck deck = deckService.getDeckById(id, sessionUser);
        model.addAttribute("deck", deck);
        model.addAttribute("userCards", deckService.getOwnedCardsNotAlreadyInDeck(deck, sessionUser));
        return "/deck/addOwnedCardToDeck";
    }

    //Adding owned card to deck
    @PostMapping("/details/{id}/addCard/{cardId}")
    public String addCardToDeck(@PathVariable int id,
                                @PathVariable int cardId,
                                HttpSession session,
                                Model model) {
        User sessionUser = (User) session.getAttribute("currentUser");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        deckService.addOwnedCardToDeckByIds(cardId, id, sessionUser);
        refreshCurrentSessionUser(session);
        return "redirect:/myDecks/details/{id}/addCard";
    }

    private void refreshCurrentSessionUser(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        session.setAttribute("currentUser", userService.refreshSessionUser(user));
    }
}
