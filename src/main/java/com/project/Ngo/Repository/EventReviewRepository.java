package com.project.Ngo.Repository;

import com.project.Ngo.model.EventReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventReviewRepository  extends JpaRepository<EventReview, Long> {
    // Additional query methods if needed
}