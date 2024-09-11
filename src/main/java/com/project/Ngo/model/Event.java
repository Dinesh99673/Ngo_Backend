package com.project.Ngo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long event_id;
    private Long ngo_id;
    private String title;
    private String description;
    private String location_link;
    private String venue;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
