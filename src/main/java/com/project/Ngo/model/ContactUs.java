package com.project.Ngo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Entity
@AllArgsConstructor
@Table(name = "contactUs")
public class ContactUs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;


    private String name;

    private String email;

    @Column(length = 1000)
    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ContactUs() {
        this.createdAt = LocalDateTime.now();
    }


}
