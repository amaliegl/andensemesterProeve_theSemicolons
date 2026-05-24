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

        if(event.getEventStatus() != EventStatus_ENUM.Aaben_for_tilmelding){
            return false;
        }

        if(currentNumberOfParticipants >= event.getMaxPlayers()){
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

    public Event getEventById(int eventId){
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

    public void updateEvent(Event event){
        eventRepository.updateEventInfo(event);
    }
}
