package com.project.Ngo.service;

import com.project.Ngo.Repository.NgoRepository;
import com.project.Ngo.model.Ngo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NgoService {

    @Autowired
    private NgoRepository ngoRepository;

    private String profileDir = System.getProperty("user.dir")+"\\uploads\\ngo_images";

    public List<Ngo> getAllngos() {
        List<Ngo> Ngos = ngoRepository.findAll();
        System.out.println("Fetched Ngo: " + Ngos);
        return Ngos;
    }

    public Optional<Ngo> getNgoById(Long id) {
        return ngoRepository.findById(id);
    }

    public Ngo saveNgo(Ngo ngo, MultipartFile profile_image) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + profile_image.getOriginalFilename();

        // Path to save the file
        System.out.println("Profile directory: " + profileDir);
        Path filePath = Paths.get(profileDir, fileName);
        String path = filePath.toString().replace("\\", "/");


        // Save the file to the specified path
        Files.write(filePath, profile_image.getBytes());

        // Save the file path in the database
        ngo.setProfile_path(path);
        ngo.setProfile_type(profile_image.getContentType());



        return ngoRepository.save(ngo);
    }

    public void deleteNgo(Long id) {
        ngoRepository.deleteById(id);
    }

}
