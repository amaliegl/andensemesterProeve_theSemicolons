package org.example.andensemesterproeve_thesemicolons.application;

import org.example.andensemesterproeve_thesemicolons.domain.Event;
import org.example.andensemesterproeve_thesemicolons.domain.EventStatus_ENUM;
import org.example.andensemesterproeve_thesemicolons.repository.IEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final IEventRepository eventRepository;

    public EventService(IEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAllEvents();
    }

    public boolean signUpForEvent(int userId, int eventId) {
        int currentNumberOfParticipants = eventRepository.getNumberOfParticipantsFromId(eventId);
        Event event = getEventById(eventId);

        if (event.getEventStatus() != EventStatus_ENUM.Aaben_for_tilmelding) {
            return false;
        }

        if (currentNumberOfParticipants >= event.getMaxPlayers()) {
            return false;
        }
        if (!eventRepository.UserIsAlreadySignedUp(userId, eventId)) {
            eventRepository.signUserUpForEvent(userId, eventId);
            if (currentNumberOfParticipants >= event.getMaxPlayers()) {
                eventRepository.updateEventStatus(eventId, EventStatus_ENUM.Fuldt_booket.name());
            }
            return true;
        }
        return true;
    }

    public Event getEventById(int eventId) {
        return eventRepository.getEventById(eventId);
    }

    public List<Event> getAllMyArrangedEvents(int userId) {
        return eventRepository.FindAllMyArrangedEvents(userId);
    }

    public List<Event> getALLmySignedUpEvents(int userId) {
        return eventRepository.findALLmySignedUpEvents(userId);
    }

    public void cancelRegistrationForEvent(int userId, int eventId) {
        eventRepository.cancelRegistrationToEvent(userId, eventId);

        Event event = eventRepository.getEventById(eventId);
        int currentNumberOfParticipants = eventRepository.getNumberOfParticipantsFromId(eventId);
        if (currentNumberOfParticipants <= event.getMaxPlayers()) {
            eventRepository.updateEventStatus(eventId, EventStatus_ENUM.Aaben_for_tilmelding.name());
        }

    }

    public void createNewEvent(Event event) {
        eventRepository.createEvent(event);
    }

    public void updateEvent(Event event) {
        eventRepository.updateEventInfo(event);
    }

    public List<Event> getAllEventsSorted(String sortBy) {
        List<Event> events = getAllEvents();

        if (sortBy != null && !sortBy.isEmpty()) {
            if (sortBy.equals("date_desc")) {
                sortByDate(events);
            } else if (sortBy.equals("openForSignUp")) {
                sortByOpenForSignUp(events);
            } else if (sortBy.equals("casual")) {
                sortByCasual(events);
            } else if (sortBy.equals("tournament")) {
                sortBYTournament(events);

            }
        }
        return events;
    }

    public void sortByDate(List<Event> events) {
        events.sort((e1, e2) -> e1.getDate().compareTo(e2.getDate()));
    }

    public void sortByOpenForSignUp(List<Event> events) {
        events.sort((e1, e2)-> e1.getEventStatus().compareTo(e2.getEventStatus()));
    }
    public void sortByCasual(List<Event> events) {
        events.sort((e1, e2) -> e1.getType().compareTo(e2.getType())); //Sorting by 1st enum on the list (casual)
    }

    public void sortBYTournament(List<Event> events) {
        events.sort((e1, e2)-> e2.getType().compareTo(e1.getType())); //Sorting by 2nd enum on the list (turnering)
    }
}
