package com.project.Ngo.controller;

import com.project.Ngo.model.Donar;
import com.project.Ngo.model.Event;
import com.project.Ngo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllevents() {
        List<Event> events = eventService.getAllevents();
        return events;
    }

    @PostMapping
    public Event saveEvent(@RequestBody Event event) {
        return eventService.saveEvent(event);
    }
}
