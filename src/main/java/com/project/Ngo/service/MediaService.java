package com.project.Ngo.service;

import com.project.Ngo.Repository.MediaRepository;
import com.project.Ngo.model.Media;
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
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Value("${upload.event_images}")
    private String eventDir;

    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    public Optional<Media> getMediaById(Long id) {
        return mediaRepository.findById(id);
    }

    public Media saveMedia(Media media, MultipartFile file_data) throws IOException {

        String fileName = UUID.randomUUID().toString() + "_" + file_data.getOriginalFilename();

        // Path to save the file
        Path filePath = Paths.get(eventDir, fileName);

        // Save the file to the specified path
        Files.write(filePath, file_data.getBytes());

        // Save the file path in the database
        media.setFile_path(filePath.toString());
        media.setFile_type(file_data.getContentType());

        return mediaRepository.save(media);
    }

    public void deleteMedia(Long id) {
        mediaRepository.deleteById(id);
    }
}
