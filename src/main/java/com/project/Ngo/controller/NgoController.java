package com.project.Ngo.controller;

import com.project.Ngo.model.Ngo;
import com.project.Ngo.service.NgoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
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
}
