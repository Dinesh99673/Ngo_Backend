package com.project.Ngo.controller;

import com.project.Ngo.model.Profile;
import com.project.Ngo.service.ProfileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173") // Enable CORS for React frontend
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    // Endpoint to get all profiles
    @GetMapping
    public List<Profile> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    // Endpoint to get a profile by ID
    @GetMapping("/{id}")
    public Optional<Profile> getProfileById(@PathVariable Long id) {
        return profileService.getProfileById(id);
    }

    // Endpoint to create a new profile with a profile image
    @PostMapping
    public ResponseEntity<?> createProfile(@ModelAttribute Profile profile, @RequestParam("profile") MultipartFile profile_image) throws IOException {
        System.out.println("Profile: " + profile);  // Add this line to print profile details
        //System.out.println("Profile Image: " + profile_image.getOriginalFilename());
        try {
            // Save profile and image
            Profile savedProfile = profileService.saveProfile(profile, profile_image);
            return ResponseEntity.ok(savedProfile); // Return the saved profile
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Here we are in Catch section");
            return ResponseEntity.status(500).body("Error uploading file"); // Handle error
        }
    }

    // Endpoint to delete a profile by ID
    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session) {

        try {
            Profile user = profileService.loginUser(email, password);

            // Store user in session
            session.setAttribute("loggedInUser", user);

            return ResponseEntity.ok("Login successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // User logout endpoint
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // Invalidate the session
        return ResponseEntity.ok("Logged out successfully");
    }
}
