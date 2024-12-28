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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NgoService {

    @Autowired
    private NgoRepository ngoRepository;

    private String profileDir = System.getProperty("user.dir") + "\\uploads\\ngo_images";
    // private String profileDir = upload.ngo_images

    public long getNgoCount() {
        return ngoRepository.countAllNgos();
    }

    public List<Ngo> getAllngos() {
        System.out.println(profileDir);
        List<Ngo> Ngos = ngoRepository.findAll();
        return Ngos;
    }

    public Optional<Ngo> getNgoById(Long id) {

        return ngoRepository.findById(id);
    }

    public Ngo saveNgo(Ngo ngo, MultipartFile profile_image) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + profile_image.getOriginalFilename();

        // Path to save the file
        Path filePath = Paths.get(profileDir, fileName);
        String path = filePath.toString().replace("\\", "/");

        // Save the file to the specified path
        Files.write(filePath, profile_image.getBytes());

        // Save the file path in the database
        ngo.setProfile_path(path);
        ngo.setProfile_type(profile_image.getContentType());

        return ngoRepository.save(ngo);
    }

    public Ngo updateNgo(Long id, Ngo ngo) {
        Optional<Ngo> optionalNgo = ngoRepository.findById(id);

        if (optionalNgo.isPresent()) {
            Ngo existingNgo = optionalNgo.get();
            existingNgo.setName(ngo.getName());
            existingNgo.setDescription(ngo.getDescription());
            existingNgo.setEmail(ngo.getEmail());
            existingNgo.setPhone(ngo.getPhone());
            existingNgo.setCategory(ngo.getCategory());
            existingNgo.setWebsite(ngo.getWebsite());
            // Update any other fields as necessary
            return ngoRepository.save(existingNgo);
        }

        return null; // or throw an exception
    }


    public void deleteNgo(Long id) {
        ngoRepository.deleteById(id);
    }

}
