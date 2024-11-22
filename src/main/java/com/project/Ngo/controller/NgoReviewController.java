package com.project.Ngo.controller;

import com.project.Ngo.model.Ngo;
import com.project.Ngo.model.NgoReview;
import com.project.Ngo.model.Profile;
import com.project.Ngo.service.NgoService;
import com.project.Ngo.service.NgoReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/NgoReview")
public class NgoReviewController {

    @Autowired
    private NgoReviewService ngoReviewService;

    @Autowired
    private NgoService ngoService;

    public NgoReviewController(NgoReviewService ngoReviewService, NgoService ngoService) {
        this.ngoReviewService = ngoReviewService;
        this.ngoService = ngoService;
    }

    @GetMapping
    public List<NgoReview> getAllNgoReviews() {
        return ngoReviewService.getAllNgoReviews();
    }

    @PostMapping
    public ResponseEntity<?> saveNgoReview(@RequestBody Map<String, Object> payload, HttpSession session) {
        // Get the logged-in user from the session
        Profile user = (Profile) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in.");
        }

        Long userId = user.getUser_id();  // Extract the user's ID
        System.out.println("Logged-in user: " + user);

        // Extract the NGO ID and rating from the payload
        int ngoId = (int) payload.get("ngo_id");
        int rating = (int) payload.get("rating");

        // Fetch the NGO from the database
        Optional<Ngo> ngo = ngoService.getNgoById((long) ngoId);
        if (ngo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NGO with ID " + ngoId + " not found.");
        }

        // Check if the user has already rated this NGO
        boolean alreadyRated = ngoReviewService.existsByNgoAndProfile(ngo.get(), user);
        if (alreadyRated) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("You have already rated this NGO.");
        }

        // Create and save the new review
        NgoReview ngoReview = new NgoReview();
        ngoReview.setRating(rating);
        ngoReview.setNgo(ngo.get());
        ngoReview.setProfile(user);

        NgoReview savedReview = ngoReviewService.saveNgoReview(ngoReview);
        return ResponseEntity.ok(savedReview);
    }

    @GetMapping("/average/{ngoId}")
    public Double getAverageRating(@PathVariable Long ngoId) {
        return ngoReviewService.getAverageRatingForNgo(ngoId);
    }


}
