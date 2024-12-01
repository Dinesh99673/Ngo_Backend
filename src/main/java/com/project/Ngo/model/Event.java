package com.project.Ngo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long event_id;

    @JsonBackReference(value = "NGO-Event-Reference")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ngo_id", nullable = false)
    private Ngo ngo;
    private Date date;
    private String title;
    private String description;
    private String location_link;
    private String venue;
    private BigDecimal fees;
    private String poster_path;
    private String poster_type;
    // Automatically set the current timestamp when the entity is first created
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp created_at;

    // Automatically update the timestamp when the entity is modified
    @UpdateTimestamp
    private Timestamp updated_at;


    @JsonManagedReference(value = "event-schedule-reference")
    @OneToMany(mappedBy = "event")
    private List<EventSchedule> eventSchedules = new ArrayList<>();

    @JsonManagedReference(value = "event-participant-reference")
    @OneToMany(mappedBy = "event")
    private List<EventParticipant> eventParticipants = new ArrayList<>();

    @JsonManagedReference(value = "Event-reference")
    @OneToMany(mappedBy = "event")
    private List<EventReview> eventReviews = new ArrayList<>();
}
