package com.project.Ngo.Repository;

import com.project.Ngo.model.Donar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonarRepository extends JpaRepository<Donar, Long> {
    // Additional query methods if needed
}
