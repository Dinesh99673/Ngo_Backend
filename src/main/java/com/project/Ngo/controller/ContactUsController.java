package com.project.Ngo.controller;

import com.project.Ngo.model.ContactUs;
import com.project.Ngo.service.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")

@RequestMapping("/contactUs")
public class ContactUsController {

    @Autowired
    private ContactUsService contactUsService;

    @PostMapping
    public ResponseEntity<String> saveContactUs(@RequestBody ContactUs contactUs) {
        ContactUs contact = contactUsService.saveContactUs(contactUs);
        return ResponseEntity.ok("Thank you for contacting us !! We will get back to you soon.");
    }
}
