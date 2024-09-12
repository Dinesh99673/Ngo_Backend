package com.project.Ngo.controller;

import com.project.Ngo.model.Profile;
import com.project.Ngo.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    public ProfileController(ProfileService profileService){

        this.profileService = profileService;
    }

    @GetMapping
    public List<Profile> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    @GetMapping("/{id}")
    public Optional<Profile> getProfileById(@PathVariable Long id) {

        return profileService.getProfileById(id);
    }

    //To add new Profile/User
    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody Profile profile,
                                 @RequestParam("profile_image") MultipartFile profile_image
                                 ) throws IOException {
        try {
            Profile savedProfile = profileService.saveProfile(profile,profile_image);
            return ResponseEntity.ok(savedProfile);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
    }
}
