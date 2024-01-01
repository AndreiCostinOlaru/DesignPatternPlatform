package com.patterns.designpatternplatform.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Pattern {

    private Long id;
    private String name;
    private String description;
    private String type;
    private String scope;
    private User owner;
    private Collection<Discussion> discussions;
    private String code;
    private Collection<PatternPhoto> photos;
    private Collection<PatternReview> reviews;
    private String publicOpinion;



    public void setId(Long id) {
        this.id = id;
    }


    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @OneToMany(mappedBy = "pattern", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Collection<PatternPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(Collection<PatternPhoto> photos) {
        this.photos = photos;
    }

    @ManyToOne
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void addPhoto(PatternPhoto photo) {
        photos.add(photo);
    }

    @Lob
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Lob
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @OneToMany(mappedBy = "pattern")
    public Collection<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(Collection<Discussion> discussions) {
        this.discussions = discussions;
    }

    @OneToMany(mappedBy = "pattern")
    public Collection<PatternReview> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<PatternReview> reviews) {
        this.reviews = reviews;
    }

    public String getPublicOpinion() {
        return publicOpinion;
    }

    public void setPublicOpinion(String publicOpinion) {
        this.publicOpinion = publicOpinion;
    }
}
