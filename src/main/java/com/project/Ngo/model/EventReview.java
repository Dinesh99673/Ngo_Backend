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
@Table(name="event_review")
public class EventReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_id;
    private Long user_id;
    private Long event_id;
    private String content;
    private float rating;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
