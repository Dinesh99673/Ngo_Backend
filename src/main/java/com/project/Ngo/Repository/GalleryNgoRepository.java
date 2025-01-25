package com.project.Ngo.Repository;

import com.project.Ngo.model.GalleryNgo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GalleryNgoRepository extends JpaRepository<GalleryNgo, Long> {
    @Query("SELECT g FROM GalleryNgo g WHERE g.ngo.ngo_id = :ngoId")
    List<GalleryNgo> findByNgoId(@Param("ngoId") Long ngoId);

}

