package com.project.Ngo.service;

import com.project.Ngo.Repository.GalleryNgoRepository;
import com.project.Ngo.Repository.NgoRepository;
import com.project.Ngo.model.Event;
import com.project.Ngo.model.GalleryNgo;
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
public class GalleryNgoService {
    @Autowired
    private  NgoRepository ngoRepository;

    @Autowired
    private GalleryNgoRepository galleryImageRepository;

    private final String galleryDir = System.getProperty("user.dir") + "\\uploads\\gallery"; // Folder where images will be saved

    public GalleryNgoService(GalleryNgoRepository galleryImageRepository) {
        this.galleryImageRepository = galleryImageRepository;
    }

    // ✅ Upload Image
    public GalleryNgo uploadImage(Long ngoId, String Name, MultipartFile file) throws IOException {
        Path dirPath = Paths.get(galleryDir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }


        Optional<Ngo> ngoOptional = ngoRepository.findById(ngoId);
        Ngo ngo = ngoOptional.orElseThrow(() -> new RuntimeException("NGO not found with ID: " + ngoId));

        // Generate unique filename
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = dirPath.resolve(fileName);

        // Save the file
        Files.write(filePath, file.getBytes());

        // 🔥 Store only relative path (NOT full path)
        String relativePath = "/uploads/gallery/" + fileName;

        // Save image details in database
        GalleryNgo image = new GalleryNgo();
        image.setNgo(ngo);
        image.setImageName(Name);
        image.setImagePath(relativePath); // ✅ Use relative path
        image.setImageType(file.getContentType());

        return galleryImageRepository.save(image);
    }


    // ✅ Get Images by NGO ID
    public List<GalleryNgo> getImagesByNgoId(Long ngoId) {
        return galleryImageRepository.findByNgoId(ngoId);
    }

    public void deleteImage(Long imageId) throws Exception {
        // Check if the image exists
        if (!galleryImageRepository.existsById(imageId)) {
            throw new Exception("Image with ID " + imageId + " does not exist.");
        }
        // Delete the image
        galleryImageRepository.deleteById(imageId);
    }
}
