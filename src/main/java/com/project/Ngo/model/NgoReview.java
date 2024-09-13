package com.project.Ngo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ngo_review")
public class NgoReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_id;
    private Long user_id;
    private Long ngo_id;
    private Long reviewed_ngo_id;
    private String content;
    private float rating;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
