package org.example.andensemesterproeve_thesemicolons.application;

import org.example.andensemesterproeve_thesemicolons.domain.Card;
import org.example.andensemesterproeve_thesemicolons.domain.Deck;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.example.andensemesterproeve_thesemicolons.domain.interfacesRepo.IDeckRepository;
import org.example.andensemesterproeve_thesemicolons.domain.interfacesRepo.IUserRepository;
import org.example.andensemesterproeve_thesemicolons.exceptions.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeckService {

    private IDeckRepository deckRepository;
    private IUserRepository userRepository;
    private final ExceptionService exceptionService;

    public DeckService(IDeckRepository deckRepository, IUserRepository userRepository, ExceptionService exceptionService) {
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
        this.exceptionService = exceptionService;
    }

    public Deck getDeckById(int deckId, User owner) {
        try {
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
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public void addOwnedCardToDeckByIds(int ownedCardId, int deckId, User owner) {
        try {
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
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public List<Card> getOwnedCardsNotAlreadyInDeck(Deck deck, User owner) {
        try {
            List<Card> ownedCards = userRepository.findAllCardsForUser(owner);

            for (int i = 0; i < deck.getCards().size(); i++) {
                for (int j = ownedCards.size() - 1; j > -1; j--) {
                    if (deck.getCards().get(i).getId() == ownedCards.get(j).getId()) {
                        ownedCards.remove(ownedCards.get(j));
                    }
                }
            }
            return ownedCards;
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public void editUserDeckInfo(User user, Deck deck) {
    //Assert that the user owns the deck before adding
        try {
            for (int i = 0; i < user.getDecks().size(); i++) {
                if (deck.getId() == user.getDecks().get(i).getId()) {
                    deckRepository.updateUserDeckInfo(user, deck);
                }
            }
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public void createNewUserDeck(User user, Deck deck) {
        try {
            deckRepository.createNewUserDeck(user, deck);
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    private void populateDeckWithListOfCards(Deck deck, List<Integer> cardIds) {
        try {
            for (Integer cardId : cardIds) {
                deck.addCard(deckRepository.findOwnedCardByOwnedCardId(cardId));
            }
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
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