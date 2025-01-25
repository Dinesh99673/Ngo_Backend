package com.project.Ngo.service;

import com.project.Ngo.DTO.EventDetailsDTO;
import com.project.Ngo.Repository.EventScheduleRepository;
import com.project.Ngo.Repository.NgoRepository;
import com.project.Ngo.model.Event;
import com.project.Ngo.Repository.EventRepository;
import com.project.Ngo.model.EventSchedule;
import com.project.Ngo.model.Ngo;
import jakarta.persistence.EntityNotFoundException;
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
    private NgoRepository ngoRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventScheduleRepository eventScheduleRepository;

    // Directory for profile images
    private final String profileDir = System.getProperty("user.dir") + "\\uploads\\ngo_images";

    public List<Event> getAllevents() {
        return eventRepository.findAll();
    }

    // Fetch Event by ID
    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public Event saveEvent(EventDetailsDTO eventDetailsDTO) throws IOException {
        // Validate EventDetailsDTO
        if (eventDetailsDTO == null) {
            throw new IllegalArgumentException("EventDetailsDTO cannot be null");
        }

        // Validate poster
        if (eventDetailsDTO.getPoster() == null || eventDetailsDTO.getPoster().isEmpty()) {
            throw new IllegalArgumentException("Poster file is required.");
        }

        // Validate NGO
        Ngo ngo = ngoRepository.findById(eventDetailsDTO.getNgo_id())
                .orElseThrow(() -> new RuntimeException("NGO not found with ID: " + eventDetailsDTO.getNgo_id()));

        // Log NGO details
        System.out.println("Saving event for NGO: " + ngo.getNgo_id());

        // Initialize Event
        Event event = new Event();
        event.setNgo(ngo);
        event.setTitle(eventDetailsDTO.getTitle());
        event.setDescription(eventDetailsDTO.getDescription());
        event.setLocation_link(eventDetailsDTO.getLocation_link());
        event.setVenue(eventDetailsDTO.getVenue());

        // Validate and Deserialize Schedules
        List<EventSchedule> eventSchedules = eventDetailsDTO.getDeserializedSchedule();
        if (eventSchedules == null || eventSchedules.isEmpty()) {
            throw new IllegalArgumentException("Event schedules cannot be empty.");
        }
        System.out.println("Deserialized Schedules: " + eventSchedules);

        // Generate unique file name for the poster
        String fileName = UUID.randomUUID().toString() + "_" + eventDetailsDTO.getPoster().getOriginalFilename();

        // Path to save the file
        Path filePath = Paths.get(profileDir, fileName);
        System.out.println("Saving poster to: " + filePath);

        // Ensure the directory exists
        Files.createDirectories(filePath.getParent());

        // Save the file
        Files.write(filePath, eventDetailsDTO.getPoster().getBytes());

        // Update Event with poster details
        event.setPoster_path(filePath.toString().replace("\\", "/")); // Ensure Unix-style paths
        event.setPoster_type(eventDetailsDTO.getPoster().getContentType());

        // Save Event to the database
        Event savedEvent = eventRepository.save(event);
        System.out.println("Event saved with ID: " + savedEvent.getEvent_id());

        // Save Event Schedules
        for (EventSchedule scheduleDTO : eventSchedules) {
            EventSchedule schedule = new EventSchedule();
            schedule.setEvent(savedEvent); // Link the schedule to the saved event
            schedule.setStart_time(scheduleDTO.getStart_time());
            schedule.setEnd_time(scheduleDTO.getEnd_time());
            schedule.setEvent_date(scheduleDTO.getEvent_date());
            eventScheduleRepository.save(schedule); // Save the schedule
            System.out.println("Saved schedule for event: " + scheduleDTO);
        }

        // Return the saved Event object
        return savedEvent;
    }

    public Event updateEvent(Long eventId, Event updatedEvent) {
        // Validate if the event exists
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        // Update event details
        existingEvent.setTitle(updatedEvent.getTitle());
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setLocation_link(updatedEvent.getLocation_link());
        existingEvent.setVenue(updatedEvent.getVenue());
        existingEvent.setPoster_path(updatedEvent.getPoster_path());
        existingEvent.setPoster_type(updatedEvent.getPoster_type());

        // Optional: Update related schedules or participants (if needed)
        if (updatedEvent.getEventSchedules() != null) {
            existingEvent.getEventSchedules().clear();
            existingEvent.getEventSchedules().addAll(updatedEvent.getEventSchedules());
            for (EventSchedule schedule : existingEvent.getEventSchedules()) {
                schedule.setEvent(existingEvent);
            }
        }

        // Save updated event
        return eventRepository.save(existingEvent);
    }

    public boolean deleteEvent(Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()) {
            eventRepository.delete(event.get());
            return true;
        } else {
            return false;
        }
    }


    public List<Event> getEventsByNgoId(Long ngoId) {
        return eventRepository.findByNgoId(ngoId);
    }



}
