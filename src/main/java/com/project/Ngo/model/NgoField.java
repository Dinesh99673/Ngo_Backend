package com.project.Ngo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LocalDateTime uploaded_at;

}
