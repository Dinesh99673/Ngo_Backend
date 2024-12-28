package com.project.Ngo.Repository;

import com.project.Ngo.model.Event;
import com.project.Ngo.model.EventParticipant;
import com.project.Ngo.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant,Long> {
    boolean existsByEventAndParticipant(Event event, Profile participant);
}
