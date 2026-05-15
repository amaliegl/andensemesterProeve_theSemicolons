package org.example.andensemesterproeve_thesemicolons.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice// fungerer som en overbygning på de andre controllere, funktioner kan deles på tværs
public class NavigationController {

    @ModelAttribute("currentUri")
    public String getCurrentUri(HttpServletRequest request){
        return request.getRequestURI();
    }
}
