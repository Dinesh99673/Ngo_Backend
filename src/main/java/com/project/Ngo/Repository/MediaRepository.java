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
    @Modifying
    @Query(value = "INSERT INTO media (ngo_id, event_id, file_data, file_type, uploaded_at) VALUES (:ngoId, :eventId, :fileData, :fileType, :uploadedAt)", nativeQuery = true)
    void saveMedia(@Param("ngoId") Long ngoId,
                   @Param("eventId") Long eventId,
                   @Param("fileData") byte[] fileData,
                   @Param("fileType") String fileType,
                   @Param("uploadedAt") LocalDateTime uploadedAt);

}
