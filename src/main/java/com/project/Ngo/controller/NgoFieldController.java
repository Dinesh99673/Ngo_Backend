package com.project.Ngo.controller;

import com.project.Ngo.model.Ngo;
import com.project.Ngo.model.NgoField;
import com.project.Ngo.service.NgoFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/field")
public class NgoFieldController {
    @Autowired
    private NgoFieldService ngoFieldService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllNgoFieldByNgoId(@PathVariable Long id) {
        try {
            List<NgoField> fields = ngoFieldService.getFieldsByNgoId(id);
            if (fields.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No fields found for the given NGO ID.");
            }
            return ResponseEntity.ok(fields);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        }
    }

        @GetMapping("/getField/{id}")
    public Optional<NgoField> getNgoFieldById(@PathVariable Long id) {
        return ngoFieldService.getNgoFieldById(id);
    }

    @GetMapping("/getFieldImage/{id}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable Long id) throws IOException {
        Optional<NgoField> field = ngoFieldService.getNgoFieldById(id);

        Path imagepath = Paths.get(field.get().getFile_path());
        String path = imagepath.toString();
        path = path.replace("\\","/");
        System.out.println(imagepath);
        Resource resource =  new FileSystemResource(imagepath.toFile());
        String contentType = Files.probeContentType(imagepath);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
    }

    //    @GetMapping("/{id}")
//    public Optional<NgoField> getNgoFieldById(@PathVariable Long id) {
//        return ngoFieldService.getNgoFieldById(id);
//    }


    @PostMapping
    public ResponseEntity<?> saveNgoField(
            @RequestParam("ngo_id") Long ngoId,  // Pass ngo_id explicitly
            @RequestParam("field_name") String fieldName,
            @RequestParam("field_content") String fieldContent,
            @RequestParam("file_data") MultipartFile fileData) throws IOException {

        try {
            NgoField ngoField = new NgoField();
            ngoField.setField_name(fieldName);
            ngoField.setField_content(fieldContent);

            // Call the service to save NgoField along with the file
            NgoField savedNgoField = ngoFieldService.saveNgoField(ngoField, fileData, ngoId);

            return ResponseEntity.ok(savedNgoField);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }
    //    @DeleteMapping("/{id}")
//    public void deleteNgoField(@PathVariable Long id) {
//        ngoFieldService.deleteNgoField(id);
//    }
}
