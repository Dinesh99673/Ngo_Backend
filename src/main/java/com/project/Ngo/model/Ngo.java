package com.project.Ngo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;




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
    private String profile_path;
    private String profile_type;
    // Automatically set the current timestamp when the entity is first created
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp created_at;

    // Automatically update the timestamp when the entity is modified
    @UpdateTimestamp
    private Timestamp updated_at;
}
