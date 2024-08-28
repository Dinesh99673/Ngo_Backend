package com.project.Ngo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="donar")
public class Donar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donar_id;
    private String type;
    private Long ngo_id;
    private Long user_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
