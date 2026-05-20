package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.Card;
import org.example.andensemesterproeve_thesemicolons.domain.CardType_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.Deck;
import org.example.andensemesterproeve_thesemicolons.domain.Rarity_ENUM;
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
    }

    @Override
    public List<Integer> findOwnedCardIdsInDeckByDeckId(int deckId) {
        String sql = "SELECT * FROM deck_cards WHERE deck_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                rs.getInt("user_owned_card_id"),
                deckId);
    }

    @Override
    public Card findOwnedCardByOwnedCardId(int ownedCardId) throws EmptyResultDataAccessException {
        String sql = """
                    SELECT * FROM user_owned_cards
                    JOIN cards ON cards.id = user_owned_cards.card_id
                    WHERE user_owned_cards.id = ?
                    """;

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

        //TODO - exception
    }

    @Override
    public void addOwnedCardToDeckByIds(int ownedCardId, int deckId) {
        String sql = """
                    INSERT INTO deck_cards (deck_id, user_owned_card_id)
                    VALUES (?, ?)
                    """;

        jdbcTemplate.update(sql, deckId, ownedCardId);
    }
}
