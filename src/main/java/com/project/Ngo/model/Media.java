package com.project.Ngo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long media_id;
    private Long ngo_id;
    private Long event_id;
    @Lob
    @Column(name = "file_data", columnDefinition = "BYTEA")
    private byte[] file_data;
    private String file_type;
    private LocalDateTime uploadedAt;
}
