package com.project.Ngo.Repository;

import com.project.Ngo.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // Additional query methods if needed
    Optional<Profile> findByEmail(String email);
}
