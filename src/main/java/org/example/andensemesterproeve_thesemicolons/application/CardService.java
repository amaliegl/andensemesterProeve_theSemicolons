package org.example.andensemesterproeve_thesemicolons.application;

import org.example.andensemesterproeve_thesemicolons.domain.Card;
import org.example.andensemesterproeve_thesemicolons.domain.CardType_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.example.andensemesterproeve_thesemicolons.repository.ICardRepository;
import org.example.andensemesterproeve_thesemicolons.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    private final ICardRepository cardRepository;

    public CardService(ICardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


    public List<String> getAllSets() {
        return cardRepository.findAllUniqueSetAbbreviations();
    }

    public List<CardType_ENUM> getAllTypes() {
        return cardRepository.findAllUniqueTypes();
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
}
