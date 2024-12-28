package com.project.Ngo.controller;

import com.project.Ngo.Repository.EventParticipantRepository;
import com.project.Ngo.model.Event;
import com.project.Ngo.model.EventParticipant;
import com.project.Ngo.model.Profile;
import com.project.Ngo.service.EventParticipantService;
import com.project.Ngo.service.EventService;
import com.project.Ngo.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/Events/EventParticipant")
public class EventParticipantController {
    @Autowired
    private EventParticipantService eventParticipantService;

    @Autowired
    private EventParticipantRepository eventParticipantRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private ProfileService profileService;

    public EventParticipantController(EventParticipantService eventParticipantService){
        this.eventParticipantService = eventParticipantService;
    }

    @GetMapping
    public List<EventParticipant> getAllEventParticipant() {
        return eventParticipantService.getAlleventParticipant();
    }

    @PostMapping
    public ResponseEntity<?> saveEventParticipant(
            @RequestParam Long eventId,
            @RequestParam Long userId) {

        // Fetch the event by its ID
        Event event = eventService.getEventById(eventId);
        if (event == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found with ID: " + eventId);
        }

        // Fetch the profile (user) by its ID
        Optional<Profile> participant = profileService.getProfileById(userId);
        if (!participant.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
        }

        // Check if the user is already registered for the event
        boolean isAlreadyRegistered = eventParticipantRepository.existsByEventAndParticipant(event, participant.get());
        if (isAlreadyRegistered) {
            // Return a response indicating that the user is already registered
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User is already registered for this event!");
        }

        // Create the EventParticipant object
        EventParticipant eventParticipant = new EventParticipant();
        eventParticipant.setEvent(event);
        eventParticipant.setParticipant(participant.get());

        // Save the event participant
        EventParticipant savedParticipant = eventParticipantService.saveEventParticipant(eventParticipant);

        // Return success response
        return ResponseEntity.status(HttpStatus.CREATED).body(savedParticipant);
    }




}
