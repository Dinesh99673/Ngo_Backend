package com.project.Ngo.model;

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
@Table(name="ngo_field")
public class NgoField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long field_id;
    private Long ngo_id;
    private String field_name;
    private String field_content;
    private String file_path;
    private String file_type;
    // Automatically update the timestamp when the entity is modified
    @UpdateTimestamp
    private Timestamp uploaded_at;

}
