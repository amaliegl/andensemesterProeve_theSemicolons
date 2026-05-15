package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryMySql implements IUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryMySql(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsernameAndPassword(String username, String password) throws EmptyResultDataAccessException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        int userId = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                (rs.getInt("id")
                ), username, password
        );

        return userId;
        //TODO - fejllogning
    }

    @Override
    public Optional<User> findById(int id) {
        String sql = """
                SELECT id, username, email, title FROM users WHERE id = ?
                """;

        List<User> results = jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        Title_ENUM.valueOf(rs.getString("title"))
                ), id
        );

        if (results.isEmpty()) {
            return Optional.empty();
        }

        results.getFirst().setCards(findAllCardsForUser(results.getFirst()));
        results.getFirst().setDecks(findAllDecksForUser(results.getFirst()));

        return Optional.of(results.getFirst());
    }

    @Override
    public List<Card> findAllCardsForUser(User user) {
        String sql = """
                SELECT * FROM user_cards
                JOIN cards ON cards.id = user_owned_cards.card
                WHERE user_id = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Card(
                        rs.getInt("cards.id"),
                        rs.getString("name"),
                        Type_ENUM.valueOf(rs.getString("type")),
                        rs.getString("set"),
                        Rarity_ENUM.valueOf(rs.getString("rarity")),
                        rs.getString("image_url"),
                        rs.getString("reference_url"),
                        rs.getBoolean("forSwapping"),
                        rs.getBoolean("visible")
                ), user.getId()
        );
    }

    @Override
    public List<Deck> findAllDecksForUser(User user) {
        String sql = """
                SELECT format, name FROM decks WHERE user_id = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        new Deck(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("format")
                        ), user.getId()
        );
    }

    @Override
    public List<Integer> findCardIdsForDeck(Deck deck) {
        String sql = "SELECT * FROM deck_cards WHERE deck_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                rs.getInt("user_owned_card_id"), deck.getId()
        );
    }
}