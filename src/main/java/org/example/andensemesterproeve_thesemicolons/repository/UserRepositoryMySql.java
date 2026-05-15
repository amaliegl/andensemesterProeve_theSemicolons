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
    public Optional<String> findPasswordByUsername(String username) {
        String sql = """
                SELECT password FROM users WHERE username = ?
                """;

        List<String> results = jdbcTemplate.query(sql, (rs, rowNum) ->
                (rs.getString("password")
                ), username
        );

        if (results.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(results.getFirst());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = """
                SELECT id, username, email, title FROM users WHERE username = ?
                """;

        List<User> results = jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        Title_ENUM.valueOf(rs.getString("title"))
                ), username
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
                SELECT * FROM user_owned_cards
                JOIN cards ON cards.id = user_owned_cards.card_id
                WHERE user_id = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Card(
                        rs.getInt("cards.id"),
                        rs.getString("name"),
                        CardType_ENUM.valueOf(rs.getString("type")),
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

    @Override
    public void createStandardUser(User user) {
        String sql = """
                INSERT INTO users (username, password, email, title)
                VALUES (?, ?, ?, 'Spiller')
                """;

        jdbcTemplate.update(sql,
                user.getUsername(), user.getPassword(), user.getEmail());
    }
}