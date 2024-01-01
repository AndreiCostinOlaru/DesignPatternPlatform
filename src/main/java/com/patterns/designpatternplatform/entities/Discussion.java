package com.patterns.designpatternplatform.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Discussion {

    private Long id;
    private String name;
    private String description;
    private User owner;
    private String code;
    private Collection<DiscussionPhoto> photos;
    private Collection<Comment> comments;
    private Pattern pattern;
    private Collection<DiscussionReview> reviews;
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


    public void setPhotos(Collection<DiscussionPhoto> photos) {
        this.photos = photos;
    }

    @ManyToOne
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    @ManyToOne
    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public void addPhoto(DiscussionPhoto photo) {
        photos.add(photo);
    }

    @OneToMany(mappedBy = "discussion")
    public Collection<DiscussionPhoto> getPhotos() {
        return photos;
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

    @OneToMany(mappedBy = "discussion")
    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "discussion")
    public Collection<DiscussionReview> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<DiscussionReview> reviews) {
        this.reviews = reviews;
    }

    public String getPublicOpinion() {
        return publicOpinion;
    }

    public void setPublicOpinion(String publicOpinion) {
        this.publicOpinion = publicOpinion;
    }
}
