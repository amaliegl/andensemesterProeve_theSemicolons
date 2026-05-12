package org.example.andensemesterproeve_thesemicolons.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Event {
    private int id;
    private String name;
    private User creator;
    private EventType_ENUM type;
    //TODO - format her
    private int maxPlayers;
    private LocalDate date;
    private LocalTime time;
    //TODO - status ENUM her (skal spørge Holger først)
    private List<User> participants;
}
