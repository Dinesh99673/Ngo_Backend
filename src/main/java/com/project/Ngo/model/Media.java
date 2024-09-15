package com.project.Ngo.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

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
    private String file_path;
    private String file_type;
    // Automatically update the timestamp when the entity is modified
    @UpdateTimestamp
    private Timestamp uploaded_at;}
