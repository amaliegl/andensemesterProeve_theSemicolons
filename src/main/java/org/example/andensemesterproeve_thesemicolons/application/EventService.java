package org.example.andensemesterproeve_thesemicolons.application;

import org.example.andensemesterproeve_thesemicolons.domain.Event;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.example.andensemesterproeve_thesemicolons.repository.IEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final IEventRepository eventRepository;

    public EventService(IEventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents(){
        return eventRepository.findAllEvents();
    }

    public void signUpForEvent(int userId, int eventId){
        eventRepository.signUserUpForEvent(userId, eventId);
    }

    public List<Event> getALLmySignedUpEvents(int userId){return eventRepository.findALLmySignedUpEvents(userId);}
}
