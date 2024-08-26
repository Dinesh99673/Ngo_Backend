package com.project.Ngo.service;

import com.project.Ngo.Repository.NgoRepository;
import com.project.Ngo.model.Ngo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NgoService {

    @Autowired
    private NgoRepository ngoRepository;

    public List<Ngo> getAllngos() {
        List<Ngo> Ngos = ngoRepository.findAll();
        System.out.println("Fetched Ngo: " + Ngos);
        return Ngos;
    }

    public Optional<Ngo> getNgoById(Long id) {
        return ngoRepository.findById(id);
    }

    public Ngo saveNgo(Ngo ngo) {
        return ngoRepository.save(ngo);
    }

    public void deleteNgo(Long id) {
        ngoRepository.deleteById(id);
    }

}
