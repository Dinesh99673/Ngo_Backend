package com.project.Ngo.Repository;

import com.project.Ngo.model.Ngo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NgoRepository extends JpaRepository<Ngo, Long> {

    @Query("SELECT n FROM Ngo n LEFT JOIN FETCH n.ngoFields WHERE n.email = :email")
    Optional<Ngo> findByEmail(@Param("email") String email);

    @Query("SELECT COUNT(n) FROM Ngo n")
    long countAllNgos();
}

