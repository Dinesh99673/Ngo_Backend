package com.project.Ngo.service;

import com.project.Ngo.DTO.EventDetailsDTO;
import com.project.Ngo.Repository.EventScheduleRepository;
import com.project.Ngo.Repository.NgoRepository;
import com.project.Ngo.model.Event;
import com.project.Ngo.Repository.EventRepository;
import com.project.Ngo.model.EventSchedule;
import com.project.Ngo.model.Ngo;
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

    public Optional<Event> getEventById(Long ngo_id) {
        return eventRepository.findById(ngo_id);
    }

    public Event saveEvent(EventDetailsDTO eventDetailsDTO) throws IOException {
        Event event = new Event();
        System.out.println("\nAbhi yhape huuu "+eventDetailsDTO.getNgo_id()+"\n");
        Ngo ngo = ngoRepository.findById(eventDetailsDTO.getNgo_id())
                .orElseThrow(() -> new RuntimeException("Ngo not found"));
        System.out.println("\n"+ngo.getNgo_id()+"\n");
        event.setNgo(ngo);
        event.setTitle(eventDetailsDTO.getTitle());
        event.setDescription(eventDetailsDTO.getDescription());
        event.setLocation_link(eventDetailsDTO.getLocation_link());
        event.setVenue(eventDetailsDTO.getVenue());
        event.setFees(eventDetailsDTO.getFees());

        List<EventSchedule> eventSchedules = eventDetailsDTO.getDeserializedSchedule();
        System.out.println("Deserialized Schedules: " + eventSchedules);

        String fileName = UUID.randomUUID().toString() + "_" + eventDetailsDTO.getPoster().getOriginalFilename();

        // Path to save the file
        Path filePath = Paths.get(profileDir, fileName);
        String path = filePath.toString().replace("\\", "/");

        // Save the file to the specified path
        Files.write(filePath,eventDetailsDTO.getPoster().getBytes());

        // Save the Data in Event object and then in database
        event.setPoster_path(path);
        event.setPoster_type(eventDetailsDTO.getPoster().getContentType());


        Event savedEvent = eventRepository.save(event);

        for (EventSchedule scheduleDTO : eventSchedules) {
            EventSchedule schedule = new EventSchedule();
            schedule.setEvent(savedEvent);
            schedule.setStart_time(scheduleDTO.getStart_time());
            schedule.setEnd_time(scheduleDTO.getEnd_time());
            schedule.setEvent_date(scheduleDTO.getEvent_date());
            eventScheduleRepository.save(schedule);
        }

        return savedEvent;
    }
//
//    public void deleteEvent(Long ngo_id) {
//        eventRepository.deleteById(ngo_id);
//    }


    public List<Event> getEventsByNgoId(Long ngoId) {
        return eventRepository.findByNgoId(ngoId);
    }



}
