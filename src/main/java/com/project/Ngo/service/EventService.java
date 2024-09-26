package com.project.Ngo.service;

import com.project.Ngo.model.Event;
import com.project.Ngo.Repository.EventRepository;
import com.project.Ngo.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Directory for profile images
    private final String profileDir = System.getProperty("user.dir") + "\\uploads\\ngo_images";

    public List<Event> getAllevents() {
        List<Event> events = eventRepository.findAll();
        return events;
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event saveEvent(Event event, MultipartFile poster_image) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + poster_image.getOriginalFilename();

        // Path to save the file
        Path filePath = Paths.get(profileDir, fileName);
        String path = filePath.toString().replace("\\", "/");

        // Save the file to the specified path
        Files.write(filePath, poster_image.getBytes());

        // Save the file path in the database
        event.setPoster_path(path);
        event.setPoster_type(poster_image.getContentType());

        return eventRepository.save(event);
    }


    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

}
