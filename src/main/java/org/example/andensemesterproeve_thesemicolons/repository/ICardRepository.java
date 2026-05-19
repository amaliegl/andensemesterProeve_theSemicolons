package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.Card;
import org.example.andensemesterproeve_thesemicolons.domain.CardType_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.User;

import java.util.List;

public interface ICardRepository {

    List<String> findAllUniqueSetAbbreviations();

    List<CardType_ENUM> findAllUniqueTypes();

    boolean checkIfCardIdExists(int cardId);

    void addCardToUserCollection(int cardId, User sessionUser);

    List<Card> findAllCards();

    List<Card> findAllCardsBySet(String set);

    List<Card> findAllCardsByType(String type);

    List<Card> findAllCardsBySetAndType(String set, String type);

    List<Card> findUserCardsByNameSearch(User user, String searchParam);
}
