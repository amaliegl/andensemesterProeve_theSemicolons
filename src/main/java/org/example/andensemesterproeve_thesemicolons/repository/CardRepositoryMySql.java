package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.Card;
import org.example.andensemesterproeve_thesemicolons.domain.CardType_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.Rarity_ENUM;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CardRepositoryMySql implements ICardRepository {

    private final JdbcTemplate jdbcTemplate;

    public CardRepositoryMySql(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> findAllUniqueSetAbbreviations() {
        String sql = "SELECT DISTINCT set_abbreviation FROM cards ORDER BY set_abbreviation ASC";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                rs.getString("set_abbreviation")
        );
    }

    public List<CardType_ENUM> findAllUniqueTypes() {
        String sql = "SELECT DISTINCT type FROM cards ORDER BY type ASC";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        CardType_ENUM.valueOf(rs.getString("type"))
        );
    }
}
