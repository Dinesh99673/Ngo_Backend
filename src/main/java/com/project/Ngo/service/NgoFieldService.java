package com.project.Ngo.service;

import com.project.Ngo.Repository.NgoFieldRepository;
import com.project.Ngo.model.NgoField;
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
public class NgoFieldService {

    @Autowired
    private NgoFieldRepository ngoFieldRepository;

    @Value("${upload.event_images}")
    private String eventDir;

    public List<NgoField> getAllNgoField() {
        return ngoFieldRepository.findAll();
    }

    public Optional<NgoField> getNgoFieldById(Long id) {
        return ngoFieldRepository.findById(id);
    }

    public NgoField saveNgoField(NgoField field, MultipartFile file_data) throws IOException {

        String fileName = UUID.randomUUID().toString() + "_" + file_data.getOriginalFilename();

        // Path to save the file
        Path filePath = Paths.get(eventDir, fileName);

        // Save the file to the specified path
        Files.write(filePath, file_data.getBytes());

        // Save the file path in the database
        field.setFile_path(filePath.toString());
        field.setFile_type(file_data.getContentType());

        return ngoFieldRepository.save(field);
    }

    public void deleteNgoField(Long id) {
        ngoFieldRepository.deleteById(id);
    }

}
