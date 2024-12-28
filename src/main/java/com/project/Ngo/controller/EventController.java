package com.project.Ngo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Ngo.DTO.EventDetailsDTO;
import com.project.Ngo.model.Event;
import com.project.Ngo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/Events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllevents() {
        return eventService.getAllevents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/ngo/{ngoId}")
    public ResponseEntity<List<Event>> getEventsByNgoId(@PathVariable Long ngoId) {
        List<Event> events = eventService.getEventsByNgoId(ngoId);
        return ResponseEntity.ok(events);
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveEvent(@ModelAttribute EventDetailsDTO eventDetailsDTO) throws IOException {
        try {
            // Save profile and image
            Event savedEvent = eventService.saveEvent(eventDetailsDTO);
            return ResponseEntity.ok(savedEvent); // Return the saved profile
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading file"); // Handle error
        }
    }
    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long eventId,
            @RequestBody Event updatedEvent) {  // Include NGO ID to ensure ownership validation

        Event event = eventService.updateEvent(eventId, updatedEvent);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        boolean isDeleted = eventService.deleteEvent(eventId);
        if (isDeleted) {
            return ResponseEntity.ok("Event deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found!");
        }
    }



    @GetMapping("/getPosterimage/{id}")
    public ResponseEntity<Resource> getPosterImage(@PathVariable Long id) throws IOException {
        Event event = eventService.getEventById(id);

        Path imagepath = Paths.get(event.getPoster_path());
        String path = imagepath.toString();
        path = path.replace("\\", "/");
        System.out.println(imagepath);
        Resource resource = new FileSystemResource(imagepath.toFile());
        String contentType = Files.probeContentType(imagepath);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
    }

}
