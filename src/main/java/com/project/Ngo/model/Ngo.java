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
    private String email;
    private String phone;
    private String address;
    private String registered_by;
    private String adhar_no;
    private String founder_name;
    private Long founded_on;
    private String category;
    private String website;
    private String location_link;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
