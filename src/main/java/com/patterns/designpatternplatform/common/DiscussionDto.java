package com.patterns.designpatternplatform.common;

import com.patterns.designpatternplatform.entities.Pattern;
import com.patterns.designpatternplatform.entities.PatternPhoto;
import com.patterns.designpatternplatform.entities.User;
import java.util.Collection;
import java.util.List;

public class DiscussionDto {
    private Long id;
    private String name;
    private String description;
    private String author;
    private String code;
    private List<DiscussionPhotoDto> photos;

    private Pattern pattern;
    private String publicOpinion;

    public String getCode() {
        return code;
    }

    public String getAuthor() {
        return author;
    }

    public List<DiscussionPhotoDto> getPhotos() {
        return photos;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPublicOpinion() {
        return publicOpinion;
    }

    public DiscussionDto(Long id, String name, Pattern pattern, String author, List<DiscussionPhotoDto> photos, String description, String code, String publicOpinion) {
        this.id = id;
        this.name = name;
        this.photos= photos;
        this.author=author;
        this.description=description;
        this.code=code;
        this.pattern=pattern;
        this.publicOpinion=publicOpinion;
    }

}
