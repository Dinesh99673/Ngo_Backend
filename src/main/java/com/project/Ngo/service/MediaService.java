package com.project.Ngo.service;

import com.project.Ngo.Repository.MediaRepository;
import com.project.Ngo.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    public List<Media> getAllMedia() {

        return mediaRepository.findAll();
    }

    public Optional<Media> getMediaById(Long id) {

        return mediaRepository.findById(id);
    }

    public Media saveMedia(Media media) {


        return mediaRepository.save(media);
    }

    public void testSaveMedia() {
        Media media = new Media();
        media.setNgo_id(3L);
        media.setEvent_id(1L);
        media.setFile_type("Image");
        media.setFile_data(new byte[]{1, 2, 3}); // Sample byte array
        media.setUploaded_at(LocalDateTime.now());

        mediaRepository.save(media);
    }

    public void deleteMedia(Long id) {

        mediaRepository.deleteById(id);
    }
}
