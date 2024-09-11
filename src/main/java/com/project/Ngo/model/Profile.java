package com.project.Ngo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;



@Entity
@Data
@Table(name="profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String adhar_no;
    @Lob
    @Column(name = "profile_image", columnDefinition = "bytea")
    private byte[] profile_image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
