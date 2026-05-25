package org.example.andensemesterproeve_thesemicolons.application;

import org.example.andensemesterproeve_thesemicolons.domain.Card;
import org.example.andensemesterproeve_thesemicolons.domain.enums.CardType_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.example.andensemesterproeve_thesemicolons.domain.interfacesRepo.ICardRepository;
import org.example.andensemesterproeve_thesemicolons.exceptions.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private final ICardRepository cardRepository;
    private final ExceptionService exceptionService;

    public CardService(ICardRepository cardRepository, ExceptionService exceptionService) {
        this.cardRepository = cardRepository;
        this.exceptionService = exceptionService;
    }

    public List<String> getAllSets() {
        try {
            return cardRepository.findAllUniqueSetAbbreviations();
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public List<CardType_ENUM> getAllTypes() {
        try {
            return cardRepository.findAllUniqueTypes();
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public List<Card> filterUserCardsBySet(User user, String set) {
        List<Card> filteredCards = new ArrayList<>();
        for (int i = 0; i < user.getCards().size(); i++) {
            if (user.getCards().get(i).getSet().equals(set)) {
                filteredCards.add(user.getCards().get(i));
            }
        }
        return filteredCards;
    }

    public List<Card> filterUserCardsByType(User user, String type) {
        List<Card> filteredCards = new ArrayList<>();
        for (int i = 0; i < user.getCards().size(); i++) {
            if (user.getCards().get(i).getType().name().equals(type)) {
                filteredCards.add(user.getCards().get(i));
            }
        }
        return filteredCards;
    }

    public List<Card> filterUserCardsBySetAndType(User user, String set, String type) {
        List<Card> filteredCards = new ArrayList<>();
        for (int i = 0; i < user.getCards().size(); i++) {
            if (user.getCards().get(i).getSet().equals(set) && user.getCards().get(i).getType().name().equals(type)) {
                filteredCards.add(user.getCards().get(i));
            }
        }
        return filteredCards;
    }

    public void addCardToUsersCollection(int cardId, User sessionUser) {
        try {
            if (cardRepository.checkIfCardIdExists(cardId)) {
                cardRepository.addCardToUserCollection(cardId, sessionUser);
            }
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public List<Card> getAllCards() {
        try {
            return cardRepository.findAllCards();
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public List<Card> getAllCardsBySet(String set) {
        try {
            return cardRepository.findAllCardsBySet(set);
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public List<Card> getAllCardsByType(String type) {
        try {
            return cardRepository.findAllCardsByType(type);
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public List<Card> getAllCardsBySetAndType(String set, String type) {
        try {
            return cardRepository.findAllCardsBySetAndType(set, type);
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public List<Card> getAllUserCardsBySearchParam(User user, String searchParam) {
        try {
            return cardRepository.findUserCardsByNameSearch(user, searchParam);
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }

    public Card getCardByUserOwnedCardId(int ownedCardId, User owner) {
        //Make sure only the owner of the cards can edit the card details of their own card
        try {
            for (int i = 0; i < owner.getCards().size(); i++) {
                if (ownedCardId == owner.getCards().get(i).getId()) {
                    Optional<Card> card = cardRepository.findUserCardByOwnedCardId(ownedCardId);
                    return card.orElse(null);
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

    public void updateUserOwnedCard(Card card) {
        try {
            cardRepository.updateUserOwnedCard(card);
        } catch (DataAccessException e) {
            exceptionService.logException(e);
            throw e;
        } catch (Exception e) {
            exceptionService.logException(e);
            throw e;
        }
    }
}
