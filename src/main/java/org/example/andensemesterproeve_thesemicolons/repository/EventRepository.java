package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EventRepository implements IEventRepository {

    private final JdbcTemplate jdbcTemplate;

    public EventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Event> findAllEvents() {
        String sql = """
                SELECT events.*, users.username
                            FROM events
                            JOIN users ON events.creator_id = users.id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            User eventCreator = new User();
            eventCreator.setId(rs.getInt("creator_id"));
            eventCreator.setUsername("username");


            return new Event(
                    rs.getInt("id"),
                    rs.getString("name"),
                    eventCreator,
                    EventType_ENUM.valueOf(rs.getString("event_type")),
                    rs.getString("format"),
                    rs.getInt("max_players"),
                    rs.getDate("event_date").toLocalDate(),
                    rs.getTime("event_time").toLocalTime(),
                    EventStatus_ENUM.valueOf(rs.getString("event_status"))
            );
        });
    }

    @Override
    public void signUserUpForEvent(int userId, int eventId) {
        String sql = """
                INSERT INTO event_users (event_id, user_id) VALUES(?,?)
                """;

        jdbcTemplate.update(sql, eventId, userId);
    }
}
