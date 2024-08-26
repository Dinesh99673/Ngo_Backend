package com.project.Ngo.Repository;

import com.project.Ngo.model.Ngo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NgoRepository extends JpaRepository<Ngo, Long> {
    // Additional query methods if needed
}
