package com.project.Ngo.controller;

import com.project.Ngo.DTO.EventDetailsDTO;
import com.project.Ngo.model.Event;
import com.project.Ngo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllevents() {
        List<Event> events = eventService.getAllevents();
        return events;
    }

    @GetMapping("/getPosterimage/{id}")
    public ResponseEntity<Resource> getPosterImage(@PathVariable Long id) throws IOException {
        Optional<Event> event = eventService.getEventById(id);

        Path imagepath = Paths.get(event.get().getPoster_path());
        String path = imagepath.toString();
        path = path.replace("\\", "/");
        System.out.println(imagepath);
        Resource resource = new FileSystemResource(imagepath.toFile());
        String contentType = Files.probeContentType(imagepath);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
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
}
