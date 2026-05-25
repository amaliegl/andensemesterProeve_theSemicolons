package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.Card;
import org.example.andensemesterproeve_thesemicolons.domain.enums.CardType_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.enums.Rarity_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.example.andensemesterproeve_thesemicolons.domain.interfacesRepo.ICardRepository;
import org.example.andensemesterproeve_thesemicolons.exceptions.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CardRepositoryMySql implements ICardRepository {

    private final JdbcTemplate jdbcTemplate;

    public CardRepositoryMySql(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> findAllUniqueSetAbbreviations() {
        String sql = "SELECT DISTINCT set_abbreviation FROM cards ORDER BY set_abbreviation ASC";

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    rs.getString("set_abbreviation")
            );
        } catch (Exception e) {
            throw new DataAccessException("Error in findAllUniqueSetAbbreviations()", e);
        }
    }

    @Override
    public List<CardType_ENUM> findAllUniqueTypes() {
            String sql = "SELECT DISTINCT type FROM cards ORDER BY type ASC";

            try {
                return jdbcTemplate.query(sql, (rs, rowNum) ->
                        CardType_ENUM.valueOf(rs.getString("type"))
                );
            } catch (Exception e) {
                throw new DataAccessException("Error in findAllUniqueTypes()", e);
            }
    }

    @Override
    public boolean checkIfCardIdExists(int cardId) {
        String sql ="SELECT id FROM cards WHERE id = ?";

        try {
            List<Integer> results = jdbcTemplate.query(sql, (rs, rowNum) ->
                    rs.getInt("id"), cardId
            );

            return !results.isEmpty();
        } catch (Exception e) {
            throw new DataAccessException("Error in checkIfCardIdExists()", e);
        }
    }

    @Override
    public void addCardToUserCollection(int cardId, User sessionUser) {
        String sql = "INSERT INTO user_owned_cards (user_id, card_id) VALUES (?, ?)";

        try {
            jdbcTemplate.update(sql, sessionUser.getId(), cardId);
        } catch (Exception e) {
            throw new DataAccessException("Error in addCardToUserCollection()", e);
        }
    }

    @Override
    public List<Card> findAllCards() {
        String sql ="SELECT * FROM cards";

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    new Card(
                            rs.getInt("id"),
                            rs.getString("name"),
                            CardType_ENUM.valueOf(rs.getString("type")),
                            rs.getString("set_abbreviation"),
                            Rarity_ENUM.valueOf(rs.getString("rarity")),
                            rs.getString("image_url"),
                            rs.getString("reference_url")
                    )
            );
        } catch (Exception e) {
            throw new DataAccessException("Error in findAllCards()", e);
        }
    }

    @Override
    public List<Card> findAllCardsBySet(String set) {
        String sql ="SELECT * FROM cards WHERE set_abbreviation = ?";

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    new Card(
                            rs.getInt("id"),
                            rs.getString("name"),
                            CardType_ENUM.valueOf(rs.getString("type")),
                            rs.getString("set_abbreviation"),
                            Rarity_ENUM.valueOf(rs.getString("rarity")),
                            rs.getString("image_url"),
                            rs.getString("reference_url")
                    ), set
            );
        } catch (Exception e) {
            throw new DataAccessException("Error in findAllCardsBySet()", e);
        }
    }

    @Override
    public List<Card> findAllCardsByType(String type) {
        String sql ="SELECT * FROM cards WHERE type = ?";

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    new Card(
                            rs.getInt("id"),
                            rs.getString("name"),
                            CardType_ENUM.valueOf(rs.getString("type")),
                            rs.getString("set_abbreviation"),
                            Rarity_ENUM.valueOf(rs.getString("rarity")),
                            rs.getString("image_url"),
                            rs.getString("reference_url")
                    ), type
            );
        } catch (Exception e) {
            throw new DataAccessException("Error in findAllCardsByType()", e);
        }
    }

    @Override
    public List<Card> findAllCardsBySetAndType(String set, String type) {
        String sql ="SELECT * FROM cards WHERE set_abbreviation = ? AND type = ?";

        try {
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    new Card(
                            rs.getInt("id"),
                            rs.getString("name"),
                            CardType_ENUM.valueOf(rs.getString("type")),
                            rs.getString("set_abbreviation"),
                            Rarity_ENUM.valueOf(rs.getString("rarity")),
                            rs.getString("image_url"),
                            rs.getString("reference_url")
                    ), set, type
            );
        } catch (Exception e) {
            throw new DataAccessException("Error in findAllCardsBySetAndType()", e);
        }
    }

    @Override
    public List<Card> findUserCardsByNameSearch(User user, String searchParam) {
        String sql = """
                SELECT * FROM user_owned_cards
                JOIN cards ON cards.id = user_owned_cards.card_id
                WHERE user_id = ?
                AND cards.name LIKE ?
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
                    ), user.getId(), "%" + searchParam + "%"
            );
        } catch (Exception e) {
            throw new DataAccessException("Error in findUserCardsByNameSearch()", e);
        }
    }

    @Override
    public Optional<Card> findUserCardByOwnedCardId(int ownedCardId) {
        String sql = """
                SELECT * FROM user_owned_cards
                JOIN cards ON cards.id = user_owned_cards.card_id
                WHERE user_owned_cards.id = ?
                """;

        try {
            List<Card> results = jdbcTemplate.query(sql, (rs, rowNum) ->
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

            if (results.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(results.getFirst());
        } catch (Exception e) {
            throw new DataAccessException("Error in findUserCardByOwnedCardId()", e);
        }
    }

    @Override
    public void updateUserOwnedCard(Card card) {
        String sql = """
                UPDATE user_owned_cards
                SET for_swapping = ?,
                card_visible = ?
                WHERE id = ?""";

        try {
            jdbcTemplate.update(sql, card.isForSwapping(), card.isVisible(), card.getId());
        } catch (Exception e) {
            throw new DataAccessException("Error in updateUserOwnedCard()", e);
        }
    }
}