package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.*;
import org.example.andensemesterproeve_thesemicolons.domain.enums.CardType_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.enums.Rarity_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.interfacesRepo.IDeckRepository;
import org.example.andensemesterproeve_thesemicolons.exceptions.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DeckRepositoryMySql implements IDeckRepository {

    private final JdbcTemplate jdbcTemplate;

    public DeckRepositoryMySql(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Deck> findDeckById(int deckId) {
        String sql = "SELECT * FROM decks WHERE id = ?";

        try {
            List<Deck> results = jdbcTemplate.query(sql, (rs, rowNum) ->
                    new Deck(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("format")
                    ), deckId
            );

            if (results.isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(results.getFirst());
        } catch (Exception e) {
            throw new DataAccessException("Error in findDeckById()", e);
        }
    }

    @Override
    public List<Integer> findOwnedCardIdsInDeckByDeckId(int deckId) {
        String sql = "SELECT * FROM deck_cards WHERE deck_id = ?";

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                            rs.getInt("user_owned_card_id"),
                    deckId);
        } catch (Exception e) {
            throw new DataAccessException("Error in findOwnedCardIdsInDeckByDeckId()", e);
        }
    }

    @Override
    public Card findOwnedCardByOwnedCardId(int ownedCardId) throws EmptyResultDataAccessException {
        String sql = """
                    SELECT * FROM user_owned_cards
                    JOIN cards ON cards.id = user_owned_cards.card_id
                    WHERE user_owned_cards.id = ?
                    """;

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
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
                    ), ownedCardId
            );
        } catch (Exception e) {
            throw new DataAccessException("Error in findOwnedCardByOwnedCardId()", e);
        }
    }

    @Override
    public void addOwnedCardToDeckByIds(int ownedCardId, int deckId) {
        String sql = """
                    INSERT INTO deck_cards (deck_id, user_owned_card_id)
                    VALUES (?, ?)
                    """;

        try {
            jdbcTemplate.update(sql, deckId, ownedCardId);
        } catch (Exception e) {
            throw new DataAccessException("Error in addOwnedCardToDeckByIds()", e);
        }
    }

    @Override
    public void updateUserDeckInfo(User user, Deck deck) {
        String sql = """
                UPDATE decks
                SET format = ?,
                name = ?
                WHERE user_id = ?
                AND id = ?
                """;

        try {
            jdbcTemplate.update(sql, deck.getFormat(), deck.getName(), user.getId(), deck.getId());
        } catch (Exception e) {
            throw new DataAccessException("Error in updateUserDeckInfo()", e);
        }
    }

    @Override
    public void createNewUserDeck(User user, Deck deck) {
        String sql = """
                INSERT INTO decks (user_id, format, name)
                VALUES (?, ?, ?)
                """;
        try {
            jdbcTemplate.update(sql, user.getId(), deck.getFormat(), deck.getName());
        } catch (Exception e) {
            throw new DataAccessException("Error in createNewUserDeck()", e);
        }
    }
}