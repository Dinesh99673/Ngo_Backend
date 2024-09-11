package com.project.Ngo.service;

import com.project.Ngo.Repository.EventReviewRepository;
import com.project.Ngo.model.EventReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventReviewService {
    @Autowired
    private EventReviewRepository eventReviewRepository;

    public List<EventReview> getAlleventReviews() {
        List<EventReview> eventReviews = eventReviewRepository.findAll();
        return eventReviews;
    }

    public Optional<EventReview> getEventReviewById(Long id) {
        return eventReviewRepository.findById(id);
    }

    public EventReview saveEventReview(EventReview eventReview) {
        return eventReviewRepository.save(eventReview);
    }

    public void deleteEventReview(Long id) {
        eventReviewRepository.deleteById(id);
    }

}
