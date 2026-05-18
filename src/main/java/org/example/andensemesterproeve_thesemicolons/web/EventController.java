package org.example.andensemesterproeve_thesemicolons.web;

import jakarta.servlet.http.HttpSession;
import org.example.andensemesterproeve_thesemicolons.application.EventService;
import org.example.andensemesterproeve_thesemicolons.domain.Event;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/myEvents")
    public String getMyEvents(){
        return "/event/myEvents";
    }

    @GetMapping("/allEvents")
    public String getAllEvents(Model model){
            List<Event> events = eventService.getAllEvents();
            model.addAttribute("events", events);
        return "/event/allEvents";
    }
}
