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
    private Timestamp start_date;
    private Timestamp end_date;
    // Automatically set the current timestamp when the entity is first created
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp created_at;

    // Automatically update the timestamp when the entity is modified
    @UpdateTimestamp
    private Timestamp updated_at;
}
