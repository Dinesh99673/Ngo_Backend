package com.project.Ngo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String name;
    private String email;
    private String phone;
    private String password;
    private String adhar_no;
    private String profile_image;
    private String image_type;
    // Automatically set the current timestamp when the entity is first created
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp created_at;

    // Automatically update the timestamp when the entity is modified
    @UpdateTimestamp
    private Timestamp updated_at;

    @JsonManagedReference(value="donor-reference")
    @OneToMany(mappedBy = "donor",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Transaction> Donations = new ArrayList<>();

    @JsonManagedReference(value = "UserReview-reference")
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<EventReview> eventReviews = new ArrayList<>();

    @JsonManagedReference(value="participant-reference")
    @OneToMany(mappedBy = "participant",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<EventParticipant> eventParticipants = new ArrayList<>();

    @JsonManagedReference(value = "User-NGO-Review")
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<NgoReview> userNgoReview = new ArrayList<>();

    @JsonManagedReference(value = "About-User-Review")
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<AboutReview> aboutUserReview = new ArrayList<>();


}
