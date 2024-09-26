package com.project.Ngo.controller;

import com.project.Ngo.model.EventReview;
import com.project.Ngo.service.EventReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/EventReview")
public class EventReviewController {
    @Autowired
    private EventReviewService eventReviewService;

    public EventReviewController(EventReviewService eventReviewService){
        this.eventReviewService = eventReviewService;
    }

    @GetMapping
    public List<EventReview> getAllEventReviews() {
        return eventReviewService.getAlleventReviews();
    }

    @PostMapping
    public EventReview saveEventReview(@RequestBody EventReview eventReview) {
        System.out.println(eventReview);
        return eventReviewService.saveEventReview(eventReview);
    }

}
