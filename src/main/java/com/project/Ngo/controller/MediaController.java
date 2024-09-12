package com.project.Ngo.controller;

import com.project.Ngo.model.Media;
import com.project.Ngo.model.Profile;
import com.project.Ngo.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
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
    public ResponseEntity<?> saveMedia(@RequestBody Media media,
                            @RequestParam("file_data") MultipartFile file_data
    ) throws IOException {
        try {
            Media savedmedia = mediaService.saveMedia(media,file_data);
            return ResponseEntity.ok(savedmedia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }


    @DeleteMapping("/{id}")
    public void deleteMedia(@PathVariable Long id) {
        mediaService.deleteMedia(id);
    }

}