package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.*;
import org.example.andensemesterproeve_thesemicolons.domain.enums.EventStatus_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.enums.EventType_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.interfacesRepo.IEventRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EventRepositoryMySql implements IEventRepository {

    private final JdbcTemplate jdbcTemplate;

    public EventRepositoryMySql(JdbcTemplate jdbcTemplate) {
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

    @Override
    public Boolean UserIsAlreadySignedUp(int userId, int eventId) {
        String sql= """
                SELECT COUNT(*) FROM event_users WHERE event_id= ? AND user_id= ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, eventId, userId); //vi bruger Integer fremfor int, fordi vi kan få returneret null
        return count != null && count>0;
    }

    @Override
    public List<Event> FindAllMyArrangedEvents(int userId) {
        String sql = """
                SELECT * FROM events
                WHERE creator_id = ?
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Event(
                        rs.getInt("id"),
                        rs.getString("name"),
                        EventType_ENUM.valueOf(rs.getString("event_type")),
                        rs.getString("format"),
                        rs.getInt("max_players"),
                        rs.getDate("event_date").toLocalDate(),
                        rs.getTime("event_time").toLocalTime(),
                        EventStatus_ENUM.valueOf(rs.getString("event_status"))
                ) ,userId
        );
    }

    @Override
    public List<Event> findALLmySignedUpEvents(int userId) {
        String sql = """
                SELECT events.* FROM events
                                INNER JOIN event_users ON events.id = event_users.event_id
                                WHERE event_users.user_id = ?
                """;
//TODO - sørg for at opdatere event_status i databasen
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Event(
                    rs.getInt("id"),
                    rs.getString("name"),
                    EventType_ENUM.valueOf(rs.getString("event_type")),
                    rs.getString("format"),
                    rs.getInt("max_players"),
                    rs.getDate("event_date").toLocalDate(),
                    rs.getTime("event_time").toLocalTime(),
                    EventStatus_ENUM.valueOf(rs.getString("event_status"))
            ) ,userId
        );
    }

    @Override
    public void cancelRegistrationToEvent(int userId, int eventId) {
        String sql = """
                DELETE FROM event_users WHERE user_id=? AND event_id=?
                """;

        jdbcTemplate.update(sql, userId, eventId);
    }

    @Override
    public void createEvent(Event event) {
       String sql = """
                INSERT INTO events (creator_id, name, event_type,format, max_players, event_date, event_time, event_status)
                VALUES (?,?,?,?,?,?,?,'Aaben_for_tilmelding' );
        """;

       jdbcTemplate.update(sql, event.getCreator().getId(), event.getName(), event.getType().name(), event.getFormat(), event.getMaxPlayers(), event.getDate(), event.getTime());
    }

    @Override
    public Event getEventById(int eventId) {

        String sql = """
                SELECT * FROM events
                                WHERE id = ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            User eventCreator = new User();
            eventCreator.setId(rs.getInt("creator_id"));

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
        }, eventId);
    }




    @Override
    public int getNumberOfParticipantsFromId(int eventId) {
        String sql= """
                SELECT COUNT(*) FROM event_users WHERE event_id= ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, eventId);
        if (count == null){
            return 0;
        }
        return count;
    }

    @Override
    public void updateEventStatus(int eventId, String newStatus) {
        String sql = """
                UPDATE events
                SET event_status = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, newStatus, eventId);

    }

    @Override
    public void updateStatusForConcludedEvents() {
        String sql = """
                UPDATE events
                SET event_status = 'Afholdt'
                WHERE event_date < CURDATE()
                AND event_status != 'Afholdt'
                """;

        jdbcTemplate.update(sql);
    }

    @Override
    public void updateStatusForOngoingEvents() {
        String sql = """
                UPDATE events
                SET event_status = 'Lukket_for_tilmelding'
                WHERE event_date <= DATE_ADD(CURDATE(), INTERVAL 1 DAY)
                AND event_status != 'Lukket_for_tilmelding'
                AND event_status != 'Afholdt'
                """;
        jdbcTemplate.update(sql);

    }

    @Override
    public void updateEventInfo(Event event) {
        String sql = """
                UPDATE events
                SET name = ?,
                event_type = ?,
                format = ?,
                max_players = ?,
                event_date = ?,
                event_time = ?
                WHERE id=?
                """;

        jdbcTemplate.update(sql,
                event.getName(),
                event.getType().name(),
                event.getFormat(),
                event.getMaxPlayers(),
                event.getDate(),
                event.getTime(),
                event.getId()
                );
    }
}
