package org.example.andensemesterproeve_thesemicolons.web;

import jakarta.servlet.http.HttpSession;
import org.example.andensemesterproeve_thesemicolons.application.EventService;
import org.example.andensemesterproeve_thesemicolons.domain.Event;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/myEvents")
    public String getMyEvents(Model model, HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        if (user == null){
            return "redirect:/login";
        }
        List<Event> mySignedUpEvents = eventService.getALLmySignedUpEvents(user.getId());
        model.addAttribute("mySignedUpEvents", mySignedUpEvents);
        return "/event/myEvents";
    }

    @GetMapping("/allEvents")
    public String getAllEvents(Model model, HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        if (user == null){
            return "redirect:/login";
        }
            List<Event> events = eventService.getAllEvents();
            model.addAttribute("events", events);
        return "/event/allEvents";
    }

    @GetMapping("/signUp/myEvents/{eventId}")
    public String signUpToEvent(@PathVariable("eventId") int eventId, HttpSession session){
        User user = (User) session.getAttribute("currentUser");
        int userId = user.getId();
        eventService.signUpForEvent(userId, eventId);
        return "redirect:/myEvents";
    }
}
