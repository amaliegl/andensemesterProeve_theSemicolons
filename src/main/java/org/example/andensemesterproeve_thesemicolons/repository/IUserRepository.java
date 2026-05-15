package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.Card;
import org.example.andensemesterproeve_thesemicolons.domain.Deck;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    int findIdByUsernameAndPassword(String username, String password) throws EmptyResultDataAccessException;

    Optional<User> findById(int id);

    List<Card> findAllCardsForUser(User user);

    List<Deck> findAllDecksForUser(User user);

    List<Integer> findCardIdsForDeck(Deck deck);

    void createStandardUser(User user);
}
