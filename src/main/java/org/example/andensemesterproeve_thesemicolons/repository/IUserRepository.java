package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.*;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    Optional<String> findPasswordByUsername(String username);

    Optional<User> findByUsername(String username);

    List<Card> findAllCardsForUser(User user);

    List<Deck> findAllDecksForUser(User user);

    List<Integer> findCardIdsForDeck(Deck deck);

    void createStandardUser(User user);

    List<User> findAllUsers();

    List<Title_ENUM> findAllUniqueTitles();

    List<User> findAllUsersByTitle(Title_ENUM title);
}
