package com.patterns.designpatternplatform.common;

import java.util.List;

public class PatternDto {
    List<PatternPhotoDto> photos;
    Long id;
    String name;
    String publicOpinion;

    public List<PatternPhotoDto> getPhotos() {
        return photos;
    }

    String type;
    String scope;
    String author;
    String description;
    String code;

    public String getCode() {
        return code;
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

    public String getType() {
        return type;
    }

    public String getScope() {
        return scope;
    }
    public String getAuthor() {
        return author;
    }

    public String getPublicOpinion() {
        return publicOpinion;
    }

    public PatternDto(Long id, String name, String type, String scope, String author, List<PatternPhotoDto> photos, String description, String code, String publicOpinion) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.scope = scope;
        this.author = author;
        this.photos= photos;
        this.description=description;
        this.code=code;
        this.publicOpinion=publicOpinion;
    }

}
