package com.project.Ngo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ngo_field")
public class NgoField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long field_id;

    // Many NgoFields are associated with one Ngo
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ngo_id", nullable = false)
    private Ngo ngo;

    private String field_name;
    private String field_content;
    private String file_path;
    private String file_type;

}
