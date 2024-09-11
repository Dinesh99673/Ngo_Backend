package com.project.Ngo.controller;

import com.project.Ngo.model.AboutReview;
import com.project.Ngo.service.AboutReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/AboutReview")
public class AboutReviewController {
    @Autowired
    private AboutReviewService aboutReviewService;

    public AboutReviewController(AboutReviewService eventReviewService){
        this.aboutReviewService = eventReviewService;
    }

    @GetMapping
    public List<AboutReview> getAllEventReviews() {
        return aboutReviewService.getAllAboutReviews();
    }

    @PostMapping
    public AboutReview saveAboutReview(@RequestBody AboutReview aboutReview) {
        return aboutReviewService.saveAboutReview(aboutReview);
    }

}
