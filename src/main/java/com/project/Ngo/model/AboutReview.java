package com.project.Ngo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="about_review")
public class AboutReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_id;
    private Long user_id;
    private Long ngo_id;
    private String content;
    private float rating;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
