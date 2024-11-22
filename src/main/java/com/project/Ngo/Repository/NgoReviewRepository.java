package com.project.Ngo.Repository;

import com.project.Ngo.model.Ngo;
import com.project.Ngo.model.NgoReview;
import com.project.Ngo.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NgoReviewRepository  extends JpaRepository<NgoReview, Long> {
    // Additional query methods if needed
    @Query("SELECT AVG(nr.rating) FROM NgoReview nr WHERE nr.ngo.ngo_id = :ngoId")
    Double findAverageRatingByNgoId(Long ngoId);



    boolean existsByNgoAndProfile(Ngo ngo, Profile profile);



}
