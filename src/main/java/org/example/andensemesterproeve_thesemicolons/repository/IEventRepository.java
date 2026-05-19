package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.Event;
import org.example.andensemesterproeve_thesemicolons.domain.User;

import java.util.List;

public interface IEventRepository {
    List<Event> findAllEvents();

    void signUserUpForEvent(int userId, int eventId);

    Boolean UserIsAlreadySignedUp(int userId, int eventId);

    List<Event> findALLmySignedUpEvents(int userId);

    void cancelRegistrationToEvent(int userId, int eventId);
}
