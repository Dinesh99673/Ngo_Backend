package com.project.Ngo.service;

import com.project.Ngo.Repository.EventScheduleRepository;
import com.project.Ngo.model.EventSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventScheduleService {
    @Autowired
    private EventScheduleRepository eventScheduleRepository;

    public List<EventSchedule> getAlleventSchedule() {
        List<EventSchedule> eventSchedules = eventScheduleRepository.findAll();
        return eventSchedules;
    }

    public Optional<EventSchedule> getEventScheduleById(Long id) {
        return eventScheduleRepository.findById(id);
    }

    public EventSchedule saveEventSchedule(EventSchedule eventSchedule) {
        return eventScheduleRepository.save(eventSchedule);
    }

    public void deleteEventSchedule(Long id) {
        eventScheduleRepository.deleteById(id);
    }

}
