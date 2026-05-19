package org.example.andensemesterproeve_thesemicolons.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Event {
    private int id;
    private String name;
    private User creator;
    private EventType_ENUM type;
    private String format;
    private int maxPlayers;
    private LocalDate date;
    private LocalTime time;
    private EventStatus_ENUM eventStatus;
    private List<User> participants;

    public Event(int id, String name, User creator, EventType_ENUM type, String format, int maxPlayers, LocalDate date, LocalTime time, EventStatus_ENUM eventStatus) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.type = type;
        this.format = format;
        this.maxPlayers = maxPlayers;
        this.date = date;
        this.time = time;
        this.eventStatus = eventStatus;
    }

    public Event(int id, String name, EventType_ENUM type, String format, int maxPlayers, LocalDate date, LocalTime time, EventStatus_ENUM eventStatus) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.format = format;
        this.maxPlayers = maxPlayers;
        this.date = date;
        this.time = time;
        this.eventStatus = eventStatus;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public User getCreator() {return creator;}
    public void setCreator(User creator) {this.creator = creator;}

    public EventType_ENUM getType() {return type;}
    public void setType(EventType_ENUM type) {this.type = type;}

    public String getFormat() {return format;}
    public void setFormat(String format) {this.format = format;}

    public int getMaxPlayers() {return maxPlayers;}
    public void setMaxPlayers(int maxPlayers) {this.maxPlayers = maxPlayers;}

    public LocalDate getDate() {return date;}
    public void setDate(LocalDate date) {this.date = date;}

    public LocalTime getTime() {return time;}
    public void setTime(LocalTime time) {this.time = time;}

    public EventStatus_ENUM getEventStatus() {return eventStatus;}
    public void setEventStatus(EventStatus_ENUM eventStatus) {this.eventStatus = eventStatus;}

    public List<User> getParticipants() {return participants;}
    public void setParticipants(List<User> participants) {this.participants = participants;}
}


