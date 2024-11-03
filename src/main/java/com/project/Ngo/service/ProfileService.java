package com.project.Ngo.service;

import com.project.Ngo.Repository.ProfileRepository;
import com.project.Ngo.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    // Directory for profile images
    private final String profileDir = System.getProperty("user.dir") + "\\uploads\\ngo_images";

    public List<Profile> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        System.out.println("Fetched profiles: " + profiles);
        return profiles;
    }

    public Optional<Profile> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    public Profile saveProfile(Profile profile, MultipartFile profile_image) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + profile_image.getOriginalFilename();

        // Path to save the file
        Path filePath = Paths.get(profileDir, fileName);
        String path = filePath.toString().replace("\\", "/");

        // Save the file to the specified path
        Files.write(filePath, profile_image.getBytes());

        // Save the file path in the database
        profile.setProfile_image(path);
        profile.setImage_type(profile_image.getContentType());

        // profile.setProfile_image("Yoooo");

        return profileRepository.save(profile);
    }

    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }

    // This method will authenticate the user using email and password
    public Profile loginUser(String email, String password) throws Exception {
        Profile user = profileRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));
        System.out.println("User Details are :- "+user.getName());

        // Compare the plain text password (in production, use password encryption)
        if (!user.getPassword().equals(password)) {
            throw new Exception("Invalid password");
        }
        return user; // User successfully authenticated
    }
}
