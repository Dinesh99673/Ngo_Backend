package com.project.Ngo.Repository;

import com.project.Ngo.model.Event;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE e.ngo.ngo_id = :ngoId")
    List<Event> findByNgoId(@Param("ngoId") Long ngoId);



}
