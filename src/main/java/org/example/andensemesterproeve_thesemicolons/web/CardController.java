package org.example.andensemesterproeve_thesemicolons.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CardController {

    @GetMapping("/myCards")
    public String getMyCards(){
        return "/cards/myCards";
    }
}
