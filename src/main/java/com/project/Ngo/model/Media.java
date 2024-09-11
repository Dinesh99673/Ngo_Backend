package com.project.Ngo.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "media")
public class Media {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long media_id;
    private Long ngo_id;
    private Long event_id;

    @Lob
    @Column(name = "file_data", columnDefinition = "bytea")
    private byte[] file_data;
    private String description;
    private LocalDateTime uploaded_at;
}
