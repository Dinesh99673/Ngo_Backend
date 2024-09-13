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
    private String profile_image;
    private String image_type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
