package com.project.Ngo.service;

import com.project.Ngo.Repository.EventParticipantRepository;
import com.project.Ngo.model.EventParticipant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventParticipantService {
    @Autowired
    private EventParticipantRepository eventParticipantRepository;

    public List<EventParticipant> getAlleventParticipant() {
        List<EventParticipant> eventParticipants = eventParticipantRepository.findAll();
        return eventParticipants;
    }

    public Optional<EventParticipant> getEventParticipantById(Long id) {
        return eventParticipantRepository.findById(id);
    }

    public EventParticipant saveEventParticipant(EventParticipant eventParticipant) {
        return eventParticipantRepository.save(eventParticipant);
    }


    public void deleteEventParticipant(Long id) {
        eventParticipantRepository.deleteById(id);
    }

}
