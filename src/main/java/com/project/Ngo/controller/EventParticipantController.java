package com.project.Ngo.controller;

import com.project.Ngo.model.EventParticipant;
import com.project.Ngo.service.EventParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/EventParticipant")
public class EventParticipantController {
    @Autowired
    private EventParticipantService eventParticipantService;

    public EventParticipantController(EventParticipantService eventParticipantService){
        this.eventParticipantService = eventParticipantService;
    }

    @GetMapping
    public List<EventParticipant> getAllEventParticipant() {
        return eventParticipantService.getAlleventParticipant();
    }

    @PostMapping
    public EventParticipant saveEventParticipant(@RequestBody EventParticipant eventParticipant) {
        return eventParticipantService.saveEventParticipant(eventParticipant);
    }
}
