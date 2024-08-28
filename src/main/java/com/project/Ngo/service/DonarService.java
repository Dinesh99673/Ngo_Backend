package com.project.Ngo.service;

import com.project.Ngo.Repository.DonarRepository;
import com.project.Ngo.model.Donar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonarService {

    @Autowired
    private DonarRepository donarRepository;

    public List<Donar> getAllDonars() {
        return donarRepository.findAll();
    }

    public Optional<Donar> getDonarById(Long id) {
        return donarRepository.findById(id);
    }

    public Donar saveDonar(Donar donar) {
        return donarRepository.save(donar);
    }

    public void deleteDonar(Long id) {
        donarRepository.deleteById(id);
    }
}
