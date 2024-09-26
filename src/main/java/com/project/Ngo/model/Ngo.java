package com.project.Ngo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


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
    private Date founded_on;
    private String category;
    private String website;
    private String location_link;
    private String password;
    @Getter
    private String profile_path;
    private String profile_type;
    // Automatically set the current timestamp when the entity is first created
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp created_at;

    // Automatically update the timestamp when the entity is modified
    @UpdateTimestamp
    private Timestamp updated_at;

    // One NGO can have many fields
    @JsonManagedReference
    @OneToMany(mappedBy = "ngo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NgoField> ngoFields = new ArrayList<>();

    @JsonManagedReference(value="donorNgo-reference")
    @OneToMany(mappedBy = "donorNgo", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Transaction> ngoDonations = new ArrayList<>();

    @JsonManagedReference(value="recipientNgo-reference")
    @OneToMany(mappedBy = "recipientNgo", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Transaction> receivedDonations = new ArrayList<>();



}
