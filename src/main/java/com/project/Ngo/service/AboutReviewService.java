package com.project.Ngo.service;

import com.project.Ngo.Repository.AboutReviewRepository;
import com.project.Ngo.model.AboutReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AboutReviewService {
    @Autowired
    private AboutReviewRepository aboutReviewRepository;

    public List<AboutReview> getAllAboutReviews() {
        return aboutReviewRepository.findAll();
    }

    public Optional<AboutReview> getAboutReviewById(Long id) {
        return aboutReviewRepository.findById(id);
    }

    public AboutReview saveAboutReview(AboutReview aboutReview) {
        return aboutReviewRepository.save(aboutReview);
    }

    public void deleteAboutReview(Long id) {
        aboutReviewRepository.deleteById(id);
    }

}
