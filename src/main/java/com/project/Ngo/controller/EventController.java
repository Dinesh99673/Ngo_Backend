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

//    @GetMapping
//    public List<Event> getAllevents() {
//        List<Event> events = eventService.getAllevents();
//        return events;
//    }

    @GetMapping("/{ngoId}")
    public ResponseEntity<List<Event>> getEventsByNgoId(@PathVariable Long ngoId) {
        List<Event> events = eventService.getEventsByNgoId(ngoId);
        return ResponseEntity.ok(events);
    }

    @PostMapping("/{ngoId}/add-event")
    public ResponseEntity<?> createEvent(@PathVariable Long ngoId,
                                         @RequestParam("newEvent") String newEvent,
                                         @RequestParam("poster") MultipartFile poster) {
        try {
            // Convert eventDetailsJson (string) to EventDetailsDTO
            ObjectMapper objectMapper = new ObjectMapper();

            EventDetailsDTO eventDetailsDTO = objectMapper.readValue(newEvent, EventDetailsDTO.class);
            System.out.println("shinde");

            System.out.println(eventDetailsDTO); // Log the object

            // Set the NGO ID in the DTO
            eventDetailsDTO.getNgo().setNgo_id(ngoId);

            // Call the service to save the event
            Event savedEvent = eventService.saveEvent(eventDetailsDTO, poster);
            return ResponseEntity.ok(savedEvent); // Return the saved event
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error parsing event data");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving event");
        }
    }



//    @GetMapping("/getPosterimage/{id}")
//    public ResponseEntity<Resource> getPosterImage(@PathVariable Long id) throws IOException {
//        Optional<Event> event = eventService.getEventById(id);
//
//        Path imagepath = Paths.get(event.get().getPoster_path());
//        String path = imagepath.toString();
//        path = path.replace("\\", "/");
//        System.out.println(imagepath);
//        Resource resource = new FileSystemResource(imagepath.toFile());
//        String contentType = Files.probeContentType(imagepath);
//
//        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
//    }
//
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> saveEvent(@ModelAttribute EventDetailsDTO eventDetailsDTO) throws IOException {
//        try {
//            // Save profile and image
//            Event savedEvent = eventService.saveEvent(eventDetailsDTO);
//            return ResponseEntity.ok(savedEvent); // Return the saved profile
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Error uploading file"); // Handle error
//        }
//    }
}
