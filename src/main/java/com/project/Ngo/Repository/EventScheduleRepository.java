package com.project.Ngo.Repository;

import com.project.Ngo.model.EventSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventScheduleRepository extends JpaRepository<EventSchedule ,Long> {
}
