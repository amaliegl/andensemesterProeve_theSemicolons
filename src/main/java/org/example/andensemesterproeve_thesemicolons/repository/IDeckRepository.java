package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.Card;
import org.example.andensemesterproeve_thesemicolons.domain.Deck;
import org.example.andensemesterproeve_thesemicolons.domain.User;

import java.util.List;
import java.util.Optional;

public interface IDeckRepository {

    Optional<Deck> findDeckById(int deckID);

    List<Integer> findOwnedCardIdsInDeckByDeckId(int deckId);

    Card findOwnedCardByOwnedCardId(int ownedCardId);

    void addOwnedCardToDeckByIds(int ownedCardId, int deckId);

    void updateUserDeckInfo(User user, Deck deck);
}
