package com.project.Ngo.controller;

import com.project.Ngo.model.Media;
import com.project.Ngo.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/media")
public class MediaController {

<<<<<<< HEAD
    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
=======
    @Value("${db.username}")
    private String dbUsername;

    @Value("${db.password}")
>>>>>>> d2b45bf5b7cf3379c5ae412f6f8e7b97720fbae3
    private String dbPassword;

    @Autowired
    private MediaService mediaService;

    @GetMapping
    public List<Media> getAllMedia() {
        return mediaService.getAllMedia();
    }

    @GetMapping("/{id}")
    public Optional<Media> getMediaById(@PathVariable Long id) {
        return mediaService.getMediaById(id);
    }

    @PostMapping
    public String saveMedia(
            @RequestParam("ngo_id") Long ngoId,
            @RequestParam("event_id") Long eventId,
<<<<<<< HEAD
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile file) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/Ngo", dbUsername, dbPassword)) {
            String sql = "INSERT INTO media (ngo_id, event_id, file_data, description, uploaded_at) VALUES (?, ?, ?, ?, ?)";
=======
            @RequestParam("file_type") String fileType,
            @RequestParam("image") MultipartFile file) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/Ngo", dbUsername, dbPassword)) {
            String sql = "INSERT INTO media (ngo_id, event_id, file_data, file_type, uploaded_at) VALUES (?, ?, ?, ?, ?)";
>>>>>>> d2b45bf5b7cf3379c5ae412f6f8e7b97720fbae3
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, ngoId);
                stmt.setLong(2, eventId);
                stmt.setBytes(3, file.getBytes());
<<<<<<< HEAD
                stmt.setString(4, description);
=======
                stmt.setString(4, "Image");
>>>>>>> d2b45bf5b7cf3379c5ae412f6f8e7b97720fbae3
                stmt.setObject(5, LocalDateTime.now());
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to save media: " + e.getMessage();
        }
        return "Saved succesfully";

    }


    @DeleteMapping("/{id}")
    public void deleteMedia(@PathVariable Long id) {
        mediaService.deleteMedia(id);
    }

<<<<<<< HEAD
}
=======
}
>>>>>>> d2b45bf5b7cf3379c5ae412f6f8e7b97720fbae3
