package com.project.Ngo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="event_schedule")
public class EventSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedule_id;

    @JsonBackReference(value="event-schedule-reference")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = true)
    private Event event;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date event_date;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime  start_time;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime end_time;

}
