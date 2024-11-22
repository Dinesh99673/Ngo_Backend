package com.project.Ngo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name="ngo_review")
public class NgoReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_id;

    @JsonBackReference(value = "User-NGO-Review")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private Profile profile;

    @JsonBackReference(value = "NGO-NGO-Review")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ngo_id", nullable = true)
    private Ngo ngo;

    @JsonBackReference(value = "Reviewed-NGO")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_ngo_id", nullable = true)
    private Ngo reviewed_ngo;

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
