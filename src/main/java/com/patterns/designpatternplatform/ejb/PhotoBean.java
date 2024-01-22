package com.patterns.designpatternplatform.ejb;

import com.patterns.designpatternplatform.common.DiscussionPhotoDto;
import com.patterns.designpatternplatform.common.PatternPhotoDto;
import com.patterns.designpatternplatform.common.PhotoEntityFactory;
import com.patterns.designpatternplatform.entities.Discussion;
import com.patterns.designpatternplatform.entities.DiscussionPhoto;
import com.patterns.designpatternplatform.entities.Pattern;
import com.patterns.designpatternplatform.entities.PatternPhoto;
import com.patterns.designpatternplatform.interfaces.PhotoFactory;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class PhotoBean {
    private static final Logger LOG = Logger.getLogger(PhotoBean.class.getName());
    private final PhotoFactory photoFactory = new PhotoEntityFactory();
    @PersistenceContext
    EntityManager entityManager;

    public List<PatternPhotoDto> copyPatternPhotosToDto(Collection<PatternPhoto> photos){//converts a list of patternPhotos into a list of patternPhotoDtos
        List<PatternPhotoDto> patternPhotoDtos=new ArrayList<>();
        for(PatternPhoto photo:photos){
            PatternPhotoDto patternPhotoDto=new PatternPhotoDto(photo.getId(),photo.getFilename(), photo.getFileType(),photo.getFileContent());
            patternPhotoDtos.add(patternPhotoDto);
        }
        return patternPhotoDtos;
    }

    public List<DiscussionPhotoDto> copyDiscussionPhotosToDto(Collection<DiscussionPhoto> photos){//converts a list of discussionPhotos into a list of discussionPhotoDtos
        List<DiscussionPhotoDto> discussionPhotoDtos=new ArrayList<>();
        for(DiscussionPhoto photo:photos){
            DiscussionPhotoDto discussionPhotoDto=new DiscussionPhotoDto(photo.getId(),photo.getFilename(), photo.getFileType(),photo.getFileContent());
            discussionPhotoDtos.add(discussionPhotoDto);
        }
        return discussionPhotoDtos;
    }
    public void createPhoto(String filename,byte[] filecontent,String filetype, Pattern pattern){//adds a pattern photo to the database
        LOG.info("createPatternPhoto");
        PatternPhoto photo = photoFactory.createPatternPhoto(filename, filecontent, filetype);
        if(photo.getFileContent().length != 0) {
            pattern.getPhotos().add(photo);
            photo.setPattern(pattern);
            entityManager.persist(photo);
        }
    }

    public void createPhoto(String filename,byte[] filecontent,String filetype, Discussion discussion){// adds a discussion photo to the database
        LOG.info("createPatternPhoto");
        DiscussionPhoto photo = photoFactory.createDiscussionPhoto(filename, filecontent, filetype);
        if(photo.getFileContent().length != 0) {
            discussion.getPhotos().add(photo);
            photo.setDiscussion(discussion);
            entityManager.persist(photo);
        }
    }

    public PatternPhotoDto findPatternPhotosById(long id) {//find a pattern photo in the database based on id
        PatternPhoto photo = entityManager
                .createQuery("SELECT p FROM PatternPhoto p where p.id = :id", PatternPhoto.class)
                .setParameter("id", id)
                .getSingleResult();
        Pattern pattern=entityManager.find(Pattern.class, photo.getPattern().getId());
        PatternPhotoDto patternPhotoDto=new PatternPhotoDto(photo.getId(),photo.getFilename(), photo.getFileType(),photo.getFileContent());
        return patternPhotoDto;
    }

    public DiscussionPhotoDto findDiscussionPhotosById(long id) {//find a discussion photo in the database based on id
        DiscussionPhoto photo = entityManager
                .createQuery("SELECT p FROM DiscussionPhoto p where p.id = :id", DiscussionPhoto.class)
                .setParameter("id", id)
                .getSingleResult();
        Discussion discussion=entityManager.find(Discussion.class,photo.getDiscussion().getId());
        DiscussionPhotoDto discussionPhotoDto=new DiscussionPhotoDto(photo.getId(),photo.getFilename(), photo.getFileType(),photo.getFileContent());
        return discussionPhotoDto;
    }
}
