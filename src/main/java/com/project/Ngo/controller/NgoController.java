package com.project.Ngo.controller;

import com.project.Ngo.model.Ngo;
import com.project.Ngo.service.NgoService;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
// @RequestMapping("/ngo")
public class NgoController {

    @Autowired
    private NgoService ngoService;

    public NgoController(NgoService ngoService) {
        this.ngoService = ngoService;
    }

    @GetMapping
    public List<Ngo> getAllngo() {
        List<Ngo> Ngos = ngoService.getAllngos();
        return Ngos;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Ngo> getNgoById(@PathVariable Long id) {
        System.out.println(id);
        Optional<Ngo> ngo = ngoService.getNgoById(id);
        return ngo.map(n -> new ResponseEntity<>(n, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getprofileimage/{id}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable Long id) throws IOException {
        Optional<Ngo> ngo = ngoService.getNgoById(id);

        Path imagepath = Paths.get(ngo.get().getProfile_path());
        String path = imagepath.toString();
        path = path.replace("\\", "/");
        System.out.println(imagepath);
        Resource resource = new FileSystemResource(imagepath.toFile());
        String contentType = Files.probeContentType(imagepath);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
    }

    @PostMapping
    public ResponseEntity<?> saveNgo(@ModelAttribute Ngo ngo,
            @RequestParam("profile") MultipartFile profile_image) throws IOException {
        try {
            Ngo savedNgo = ngoService.saveNgo(ngo, profile_image);
            return ResponseEntity.ok(savedNgo);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ngo> updateNgo(@PathVariable Long id, @RequestBody Ngo ngo) {
        Ngo updatedNgo = ngoService.updateNgo(id, ngo);
        if (updatedNgo != null) {
            return ResponseEntity.ok(updatedNgo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
