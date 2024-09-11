package com.project.Ngo.controller;

import com.project.Ngo.model.NgoReview;
import com.project.Ngo.service.NgoReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/NgoReview")
public class NgoReviewController {
    @Autowired
    private NgoReviewService ngoReviewService;

    public NgoReviewController(NgoReviewService ngoReviewService){
        this.ngoReviewService = ngoReviewService;
    }

    @GetMapping
    public List<NgoReview> getAllNgoReviews() {
        return ngoReviewService.getAllNgoReviews();
    }

    @PostMapping
    public NgoReview saveEventReview(@RequestBody NgoReview ngoReview) {
        return ngoReviewService.saveNgoReview(ngoReview);
    }

}
