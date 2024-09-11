package com.project.Ngo.Repository;

import com.project.Ngo.model.NgoReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NgoReviewRepository  extends JpaRepository<NgoReview, Long> {
    // Additional query methods if needed
}
