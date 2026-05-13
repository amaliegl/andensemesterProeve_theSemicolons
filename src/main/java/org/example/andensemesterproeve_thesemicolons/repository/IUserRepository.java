package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.Card;
import org.example.andensemesterproeve_thesemicolons.domain.Deck;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    public int findIdByUsernameAndPassword(String username, String password) throws EmptyResultDataAccessException;

    public Optional<User> findById(int id);

    public List<Card> findAllCardsForUser(User user);

    public List<Deck> findAllDecksForUser(User user);

    public List<Integer> findCardIdsForDeck(Deck deck);
}
