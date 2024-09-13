package com.project.Ngo.controller;

import com.project.Ngo.model.Ngo;
import com.project.Ngo.service.NgoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Ngo> saveNgo(@RequestBody Ngo ngo) {
        Ngo savedNgo = ngoService.saveNgo(ngo);
        return new ResponseEntity<>(savedNgo, HttpStatus.CREATED);
    }
}
