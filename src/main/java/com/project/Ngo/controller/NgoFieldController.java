package com.project.Ngo.controller;

import com.project.Ngo.model.NgoField;
import com.project.Ngo.service.NgoFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ngoField")
public class NgoFieldController {
    @Autowired
    private NgoFieldService ngoFieldService;

    @GetMapping
    public List<NgoField> getAllNgoField() {
        return ngoFieldService.getAllNgoField();
    }

    @GetMapping("/{id}")
    public Optional<NgoField> getNgoFieldById(@PathVariable Long id) {
        return ngoFieldService.getNgoFieldById(id);
    }

    @PostMapping
    public ResponseEntity<?> saveNgoField(@RequestBody NgoField ngoField,
                                       @RequestParam("file_data") MultipartFile file_data
    ) throws IOException {
        try {
            NgoField savedNgoField = ngoFieldService.saveNgoField(ngoField,file_data);
            return ResponseEntity.ok(savedNgoField);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }


    @DeleteMapping("/{id}")
    public void deleteNgoField(@PathVariable Long id) {
        ngoFieldService.deleteNgoField(id);
    }

}
