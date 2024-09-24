package com.project.Ngo.service;

import com.project.Ngo.Repository.NgoFieldRepository;
import com.project.Ngo.Repository.NgoRepository;
import com.project.Ngo.model.Ngo;
import com.project.Ngo.model.NgoField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NgoFieldService {

    @Autowired
    private NgoFieldRepository ngoFieldRepository;

    @Autowired
    private NgoRepository ngoRepository;

  //@Value("${upload.fields_images}")
  //private String eventDir;
    private String eventDir = System.getProperty("user.dir") + "/uploads/relatedFieldsImages";

    public List<NgoField> getFieldsByNgoId(Long ngoId) {
        Optional<Ngo> ngoOptional = ngoRepository.findById(ngoId);
        return ngoOptional.map(Ngo::getNgoFields).orElse(Collections.emptyList());
    }

    public Optional<NgoField> getNgoFieldById(Long id) {
        return ngoFieldRepository.findById(id);
    }


    public NgoField saveNgoField(NgoField field, MultipartFile fileData, Long ngoId) throws IOException {

        // Check if the NGO exists
        Optional<Ngo> ngoOptional = ngoRepository.findById(ngoId);
        if (ngoOptional.isEmpty()) {
            throw new IllegalArgumentException("NGO not found");
        }
        field.setNgo(ngoOptional.get());  // Set the associated NGO

        // Generate a unique file name
        String fileName = UUID.randomUUID().toString() + "_" + fileData.getOriginalFilename();

        // Path to save the file (ensure the directory exists)
        Path filePath = Paths.get(eventDir, fileName);
        Files.createDirectories(filePath.getParent());  // Create directory if it doesn't exist

        // Save the file to the specified path
        Files.write(filePath, fileData.getBytes());

        // Save the file path and other details in the NgoField object
        field.setFile_path(filePath.toString().replace("\\", "/"));  // Use Unix-style paths
        field.setFile_type(fileData.getContentType());

        // Save the field to the database
        return ngoFieldRepository.save(field);
    }

    //
//    public void deleteNgoField(Long id) {
//        ngoFieldRepository.deleteById(id);
//    }
}
