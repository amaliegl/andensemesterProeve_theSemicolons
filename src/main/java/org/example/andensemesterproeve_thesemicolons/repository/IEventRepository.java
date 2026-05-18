package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.Event;

import java.util.List;

public interface IEventRepository {
    List<Event> findAllEvents();
}
