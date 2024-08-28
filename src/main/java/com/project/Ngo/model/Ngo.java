package com.project.Ngo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;



@Entity
@Data
@Table(name="ngo")
public class Ngo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ngo_id;
    private String name;
    private String description;
    private String website;
    private String email;
    private String phone;
    private String address;
    private Long registered_by;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
