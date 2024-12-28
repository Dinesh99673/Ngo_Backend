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

    public NgoField updateRelatedField(Long ngoId, Long fieldId, String fieldName, String fieldContent, MultipartFile fileData) throws IOException {
        // Check if the NGO exists
        Optional<Ngo> ngoOptional = ngoRepository.findById(ngoId);
        if (ngoOptional.isEmpty()) {
            throw new IllegalArgumentException("NGO not found");
        }

        // Fetch the existing field
        Optional<NgoField> fieldOptional = ngoFieldRepository.findById(fieldId);
        if (fieldOptional.isEmpty()) {
            throw new IllegalArgumentException("Field not found");
        }

        NgoField existingField = fieldOptional.get();

        // Verify that the field belongs to the provided NGO
        if (!existingField.getNgo().getNgo_id().equals(ngoId)) {
            throw new IllegalArgumentException("Field does not belong to the specified NGO");
        }

        // Update field details
        existingField.setField_name(fieldName);
        existingField.setField_content(fieldContent);


        // If a new file is provided, process and save it
        if (fileData != null && !fileData.isEmpty()) {
            // Generate a unique file name
            String fileName = UUID.randomUUID().toString() + "_" + fileData.getOriginalFilename();

            // Ensure `eventDir` does not contain redundant paths
            Path filePath = Paths.get(eventDir,fileName);

            // Print to check the final file path
            System.out.println("File path: " + eventDir);

            // Create directories if they don't exist
            Files.createDirectories(filePath.getParent());

            // Save the file to the specified path
            Files.write(filePath, fileData.getBytes());
            // Update the file details in the NgoField object
            existingField.setFile_path(filePath.toString().replace("\\", "/")); // Normalize to avoid redundant paths
            existingField.setFile_type(fileData.getContentType());
        }

        // Save the updated field to the database
        return ngoFieldRepository.save(existingField);
    }




    public void deleteNgoField(Long id) {
        ngoFieldRepository.deleteById(id);
    }
}
