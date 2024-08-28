package com.project.Ngo.controller;

import com.project.Ngo.model.Donar;
import com.project.Ngo.service.DonarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/donars")
public class DonarController {

    @Autowired
    private DonarService donarService;

    @GetMapping
    public List<Donar> getAllDonars() {
        return donarService.getAllDonars();
    }

    @GetMapping("/{id}")
    public Optional<Donar> getDonarById(@PathVariable Long id) {
        return donarService.getDonarById(id);
    }

    @PostMapping
    public Donar saveDonar(@RequestBody Donar donar) {
        return donarService.saveDonar(donar);
    }

    @DeleteMapping("/{id}")
    public void deleteDonar(@PathVariable Long id) {
        donarService.deleteDonar(id);
    }
}
