package org.example.andensemesterproeve_thesemicolons.application;

import org.example.andensemesterproeve_thesemicolons.domain.Card;
import org.example.andensemesterproeve_thesemicolons.domain.Deck;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.example.andensemesterproeve_thesemicolons.repository.IDeckRepository;
import org.example.andensemesterproeve_thesemicolons.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeckService {

    private IDeckRepository deckRepository;
    private IUserRepository userRepository;

    public DeckService(IDeckRepository deckRepository, IUserRepository userRepository) {
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
    }

    public Deck getDeckById(int deckId, User owner) {
        for (int i = 0; i < owner.getDecks().size(); i++) {
            if (deckId == owner.getDecks().get(i).getId()) {
                Optional<Deck> deck = deckRepository.findDeckById(deckId);
                if (deck.isEmpty()) {
                    return null;
                }

                Deck foundDeck = deck.get();
                List<Integer> ownedCardIds = deckRepository.findOwnedCardIdsInDeckByDeckId(deckId);

                populateDeckWithListOfCards(foundDeck, ownedCardIds);
                return foundDeck;
            }
        }
        return null;
    }

    public void addOwnedCardToDeckByIds(int ownedCardId, int deckId, User owner) {
        //Assert that the user owns the deck before adding
        for (int i = 0; i < owner.getDecks().size(); i++) {
            if (deckId == owner.getDecks().get(i).getId()) {
                //Assert the card is not in the deck already
                if (!cardIsAlreadyInDeck(ownedCardId, owner.getDecks().get(i))) {
                    //Assert that the user owns the card before adding
                    for (int j = 0; j < owner.getCards().size(); j++) {
                        if (ownedCardId == owner.getCards().get(j).getId()) {
                            deckRepository.addOwnedCardToDeckByIds(ownedCardId, deckId);
                        }
                    }
                }
            }
        }
    }

    public List<Card> getOwnedCardsNotAlreadyInDeck(Deck deck, User owner) {
        List<Card> ownedCards = userRepository.findAllCardsForUser(owner);

        for (int i = 0; i < deck.getCards().size(); i++) {
            for (int j = ownedCards.size() - 1; j > -1; j--) {
                if (deck.getCards().get(i).getId() == ownedCards.get(j).getId()) {
                    ownedCards.remove(ownedCards.get(j));
                }
            }
        }
        return ownedCards;
    }

    public void editUserDeckInfo(User user, Deck deck) {
    //Assert that the user owns the deck before adding
            for (int i = 0; i < user.getDecks().size(); i++) {
                if (deck.getId() == user.getDecks().get(i).getId()) {
                    deckRepository.updateUserDeckInfo(user, deck);
                }
            }
    }

    private void populateDeckWithListOfCards(Deck deck, List<Integer> cardIds) {
        for (int i = 0; i < cardIds.size(); i++) {
            deck.addCard(deckRepository.findOwnedCardByOwnedCardId(cardIds.get(i)));
        }
    }

    private boolean cardIsAlreadyInDeck(int cardId, Deck deck) {
        for (int i = 0; i < deck.getCards().size(); i++) {
            if (deck.getCards().get(i).getId() == cardId) {
                return true;
            }
        }
        return false;
    }
}
