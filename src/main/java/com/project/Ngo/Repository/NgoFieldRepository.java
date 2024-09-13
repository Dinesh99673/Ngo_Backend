package com.project.Ngo.Repository;

import com.project.Ngo.model.NgoField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NgoFieldRepository  extends JpaRepository<NgoField, Long> {
}
