package com.project.Ngo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "gallery_images")
public class GalleryNgo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference(value = "NGO-gallery-Reference")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ngo_id",referencedColumnName = "ngo_id", nullable = true)
    private Ngo ngo;

    private String imageName;
    private String imagePath;
    private String imageType;


    public GalleryNgo() {
    }

    public GalleryNgo(Ngo ngo, String imageName, String imagePath, String imageType) {
        this.ngo = ngo;
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.imageType = imageType;
    }
}