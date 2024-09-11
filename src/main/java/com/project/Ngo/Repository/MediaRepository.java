package com.project.Ngo.Repository;

import com.project.Ngo.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    // Additional query methods if needed
}
