package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.Event;
import org.example.andensemesterproeve_thesemicolons.domain.EventStatus_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.User;

import java.util.List;

public interface IEventRepository {
    List<Event> findAllEvents();

    void signUserUpForEvent(int userId, int eventId);

    Boolean UserIsAlreadySignedUp(int userId, int eventId);

    Event getEventById(int eventId);

    int getNumberOfParticipantsFromId(int eventId);

    void updateEventStatus(int eventId, String newStatus);

    List<Event> FindAllMyArrangedEvents(int userId);

    List<Event> findALLmySignedUpEvents(int userId);

    void cancelRegistrationToEvent(int userId, int eventId);

    void createEvent(Event event);
}
