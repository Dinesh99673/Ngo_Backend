package com.project.Ngo.service;

import com.project.Ngo.Repository.NgoReviewRepository;
import com.project.Ngo.model.NgoReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NgoReviewService {
    @Autowired
    private NgoReviewRepository ngoReviewRepository;

    public List<NgoReview> getAllNgoReviews() {
        return ngoReviewRepository.findAll();
    }

    public Optional<NgoReview> getNgoReviewById(Long id) {
        return ngoReviewRepository.findById(id);
    }

    public NgoReview saveNgoReview(NgoReview ngoReview) {
        return ngoReviewRepository.save(ngoReview);
    }

    public void deleteNgoReview(Long id) {
        ngoReviewRepository.deleteById(id);
    }

}
