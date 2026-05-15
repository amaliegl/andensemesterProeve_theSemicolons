package org.example.andensemesterproeve_thesemicolons.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {

    @GetMapping("/myEvents")
    public String getMyEvents(){
        return "/event/myEvents";
    }

    @GetMapping("/allEvents")
    public String getAllEvents(){
        return "/event/allEvents";
    }
}
