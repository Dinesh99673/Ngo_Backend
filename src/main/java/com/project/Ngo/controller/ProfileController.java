package com.project.Ngo.controller;

import com.project.Ngo.model.Ngo;
import com.project.Ngo.model.Profile;
import com.project.Ngo.service.ProfileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/Profile")
public class ProfileController implements Serializable {
    private static final long serialVersionUID = 1L;
    @Autowired
    private ProfileService profileService;

    // Get all profiles
    @GetMapping
    public ResponseEntity<List<Profile>> getAllProfiles() {
        List<Profile> profiles = profileService.getAllProfiles();
        return ResponseEntity.ok(profiles);
    }

    // Get account details from session
    @GetMapping("/account")
    public ResponseEntity<?> getAccountDetails(HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        String role = (String) session.getAttribute("role");

        if (id != null && role != null) {
            return ResponseEntity.ok(Map.of(
                    "id", id,
                    "role", role
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "error", "User not logged in"
            ));
        }
    }



    // Get profile by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable Long id) {
        Optional<Profile> profile = profileService.getProfileById(id);

        if (profile.isPresent()) {
            return ResponseEntity.ok(profile.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found");
        }
    }

    // Create a profile with an image
    @PostMapping
    public ResponseEntity<?> createProfile(@ModelAttribute Profile profile,
                                           @RequestParam("profile") MultipartFile profileImage) {
        try {
            Profile savedProfile = profileService.saveProfile(profile, profileImage);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }

    // Get profile image by ID
    @GetMapping("/getprofileimage/{id}")
    public ResponseEntity<?> getProfileImage(@PathVariable Long id) {
        try {
            Optional<Profile> profile = profileService.getProfileById(id);

            if (profile.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found");
            }

            Path imagePath = Paths.get(profile.get().getProfile_image());
            if (!Files.exists(imagePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image file not found");
            }

            FileSystemResource resource = new FileSystemResource(imagePath);
            String contentType = Files.probeContentType(imagePath);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving image");
        }
    }

    // Delete profile by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long id) {
        try {
            profileService.deleteProfile(id);
            return ResponseEntity.ok("Profile deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting profile");
        }
    }

    // Login endpoint for NGO/User
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password, @RequestParam String role, HttpSession session) {
        try {
            if ("ngo".equalsIgnoreCase(role)) {
                Ngo ngo = profileService.loginNgo(email, password); // Verify NGO credentials
                session.setAttribute("id", ngo.getNgo_id()); // Store only the ID
                session.setAttribute("role", "ngo");     // Store the role
                return ResponseEntity.ok(Map.of(
                        "message", "Login successful",
                        "id", ngo.getNgo_id(),
                        "role", "ngo"
                ));
            } else if ("user".equalsIgnoreCase(role)) {
                Profile user = profileService.loginUser(email, password); // Verify User credentials
                session.setAttribute("id", user.getUser_id()); // Store only the ID
                session.setAttribute("role", "user");     // Store the role
                return ResponseEntity.ok(Map.of(
                        "message", "Login successful",
                        "id", user.getUser_id(),
                        "role", "user"
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Invalid role"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }


    // Logout endpoint
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }
}