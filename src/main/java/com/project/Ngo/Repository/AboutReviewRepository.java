package com.project.Ngo.Repository;

import com.project.Ngo.model.AboutReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutReviewRepository  extends JpaRepository<AboutReview, Long> {
    // Additional query methods if needed
}