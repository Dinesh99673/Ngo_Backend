package com.project.Ngo.controller;

import com.project.Ngo.model.GalleryNgo;
import com.project.Ngo.service.GalleryNgoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/gallery")
@CrossOrigin(origins = "http://localhost:5173")
public class GalleryNgoController {

    private final GalleryNgoService galleryNgoService;

    public GalleryNgoController(GalleryNgoService galleryNgoService) {
        this.galleryNgoService = galleryNgoService;
    }

    // ✅ Upload Image
    @PostMapping("/upload/{ngoId}")
    public ResponseEntity<GalleryNgo> uploadImage(
            @PathVariable Long ngoId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("imageName") String imageName) {
        try {
            System.out.println(ngoId);
            GalleryNgo savedImage = galleryNgoService.uploadImage(ngoId, imageName,file);
            return ResponseEntity.ok(savedImage);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // ✅ Get Images for an NGO
    @GetMapping("/{ngoId}")
    public ResponseEntity<List<GalleryNgo>> getImagesByNgoId(@PathVariable Long ngoId) {
        return ResponseEntity.ok(galleryNgoService.getImagesByNgoId(ngoId));
    }

    @DeleteMapping("/image/{imageId}")
    public ResponseEntity<String> deleteImage(@PathVariable Long imageId) {
        try {
            galleryNgoService.deleteImage(imageId);
            return ResponseEntity.ok("Image deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
