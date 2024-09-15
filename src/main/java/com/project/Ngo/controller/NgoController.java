package com.project.Ngo.controller;

import com.project.Ngo.model.Ngo;
import com.project.Ngo.model.Profile;
import com.project.Ngo.service.NgoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/ngo")
public class NgoController {

    @Autowired
    private NgoService ngoService;

    public NgoController(NgoService ngoService){
        this.ngoService = ngoService;
    }

    @GetMapping
    public List<Ngo> getAllngo() {
        List<Ngo> ngos = ngoService.getAllngos();
        return ngos;
    }

    @PostMapping
    public ResponseEntity<?> saveNgo(@ModelAttribute Ngo ngo,
                                           @RequestParam("profile") MultipartFile profile_image
    ) throws IOException {
        try {
            Ngo savedNgo = ngoService.saveNgo(ngo,profile_image);
            return ResponseEntity.ok(savedNgo);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }

}
