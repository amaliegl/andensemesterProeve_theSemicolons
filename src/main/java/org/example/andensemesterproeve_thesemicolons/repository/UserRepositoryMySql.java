package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.*;
import org.example.andensemesterproeve_thesemicolons.domain.enums.CardType_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.enums.Rarity_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.enums.Title_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.interfacesRepo.IUserRepository;
import org.example.andensemesterproeve_thesemicolons.exceptions.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryMySql implements IUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryMySql(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<String> findPasswordByUsername(String username) {
        String sql = """
                SELECT password FROM users WHERE username = ?
                """;

        try {
            List<String> results = jdbcTemplate.query(sql, (rs, rowNum) ->
                    (rs.getString("password")
                    ), username
            );

            if (results.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(results.getFirst());
        } catch (Exception e) {
            throw new DataAccessException("Error in findPasswordByUsername()", e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = """
                SELECT id, username, email, title FROM users WHERE username = ?
                """;

        try {
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
        } catch (Exception e) {
            throw new DataAccessException("Error in findByUsername()", e);
        }
    }

    @Override
    public List<Card> findAllCardsForUser(User user) {
        String sql = """
                SELECT * FROM user_owned_cards
                JOIN cards ON cards.id = user_owned_cards.card_id
                WHERE user_id = ?
                """;

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    new Card(
                            rs.getInt("user_owned_cards.id"),
                            rs.getString("name"),
                            CardType_ENUM.valueOf(rs.getString("type")),
                            rs.getString("set_abbreviation"),
                            Rarity_ENUM.valueOf(rs.getString("rarity")),
                            rs.getString("image_url"),
                            rs.getString("reference_url"),
                            rs.getBoolean("for_swapping"),
                            rs.getBoolean("card_visible")
                    ), user.getId()
            );
        } catch (Exception e) {
            throw new DataAccessException("Error in findAllCardsForUser()", e);
        }
    }

    @Override
    public List<Deck> findAllDecksForUser(User user) {
        String sql = """
                SELECT * FROM decks WHERE user_id = ?
                """;

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    new Deck(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("format")
                    ), user.getId()
            );
        } catch (Exception e) {
            throw new DataAccessException("Error in findAllDecksForUser()", e);
        }
    }

    @Override
    public List<Integer> findCardIdsForDeck(Deck deck) {
        String sql = "SELECT * FROM deck_cards WHERE deck_id = ?";

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    rs.getInt("user_owned_card_id"), deck.getId()
            );
        } catch (Exception e) {
            throw new DataAccessException("Error in findCardIdsForDeck()", e);
        }
    }

    @Override
    public void createStandardUser(User user) {
        String sql = """
                INSERT INTO users (username, password, email, title)
                VALUES (?, ?, ?, 'Spiller')
                """;

        try {
            jdbcTemplate.update(sql,
                    user.getUsername(), user.getPassword(), user.getEmail());
        } catch (Exception e) {
            throw new DataAccessException("Error in createStandardUser()", e);
        }
    }

    @Override
    public List<User> findAllUsers() {
        String sql = "SELECT username, title FROM users ORDER BY username ASC";
        //Intentionally selecting specific columns to avoid fetching passwords

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    new User(
                            rs.getString("username"),
                            Title_ENUM.valueOf(rs.getString("title"))
                    )
            );
        } catch (Exception e) {
            throw new DataAccessException("Error in findAllUsers()", e);
        }
    }

    @Override
    public List<Title_ENUM> findAllUniqueTitles() {
        String sql = "SELECT DISTINCT title FROM users ORDER BY title ASC";

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    Title_ENUM.valueOf(rs.getString("title"))
            );
        } catch (Exception e) {
            throw new DataAccessException("Error in findAllUniqueTitles()", e);
        }
    }

    @Override
    public List<User> findAllUsersByTitle(Title_ENUM title) {
        String sql = "SELECT username, title FROM users WHERE title = ? ORDER BY username ASC";
        //Intentionally selecting specific columns to avoid fetching passwords
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    new User(
                            rs.getString("username"),
                            Title_ENUM.valueOf(rs.getString("title"))
                    ), title.name()
            );
        } catch (Exception e) {
            throw new DataAccessException("Error in findAllUsersByTitle()", e);
        }
    }

    @Override
    public void adminEditUser(User user) {
        String sql = """
                UPDATE users SET
                    title = ?
                WHERE username = ?
                """;

        try {
            jdbcTemplate.update(sql, user.getTitle().name(), user.getUsername());
        } catch (Exception e) {
            throw new DataAccessException("Error in adminEditUser()", e);
        }
    }

    @Override
    public Optional<User> adminFindUserByUsername(String username) {
        String sql = "SELECT username, title FROM users WHERE username = ?";

        try {
            List<User> results = jdbcTemplate.query(sql, (rs, rowNum) ->
                    new User(
                            rs.getString("username"),
                            Title_ENUM.valueOf(rs.getString("title"))
                    ), username
            );

            if (results.isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(results.getFirst());
        } catch (Exception e) {
            throw new DataAccessException("Error in adminFindUserByUsername()", e);
        }
    }
}