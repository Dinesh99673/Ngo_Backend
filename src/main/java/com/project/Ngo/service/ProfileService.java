package com.project.Ngo.service;

import com.project.Ngo.model.Profile;
import com.project.Ngo.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private String profileDir = System.getProperty("user.dir")+"\\uploads\\profile_images";

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
        System.out.println("Profile directory: " + profileDir);
        Path filePath = Paths.get(profileDir, fileName);
        String path = filePath.toString().replace("\\", "/");


        // Save the file to the specified path
        Files.write(filePath, profile_image.getBytes());

        // Save the file path in the database
        profile.setProfile_image(path);
        profile.setImage_type(profile_image.getContentType());

        return profileRepository.save(profile);
    }

    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }
}
