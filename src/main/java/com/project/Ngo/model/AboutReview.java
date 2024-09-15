package com.project.Ngo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="about_review")
public class AboutReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_id;
    private Long user_id;
    private Long ngo_id;
    private String content;
    private float rating;
    // Automatically set the current timestamp when the entity is first created
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp created_at;

    // Automatically update the timestamp when the entity is modified
    @UpdateTimestamp
    private Timestamp updated_at;
}
