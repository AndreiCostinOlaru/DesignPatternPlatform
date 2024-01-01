package com.patterns.designpatternplatform.ejb;

import com.patterns.designpatternplatform.common.DiscussionPhotoDto;
import com.patterns.designpatternplatform.common.PatternPhotoDto;
import com.patterns.designpatternplatform.entities.Discussion;
import com.patterns.designpatternplatform.entities.DiscussionPhoto;
import com.patterns.designpatternplatform.entities.Pattern;
import com.patterns.designpatternplatform.entities.PatternPhoto;
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
    @PersistenceContext
    EntityManager entityManager;

    public List<PatternPhotoDto> copyPatternPhotosToDto(Collection<PatternPhoto> photos){
        List<PatternPhotoDto> patternPhotoDtos=new ArrayList<>();
        for(PatternPhoto photo:photos){
            PatternPhotoDto patternPhotoDto=new PatternPhotoDto(photo.getId(),photo.getFilename(), photo.getFileType(),photo.getFileContent());
            patternPhotoDtos.add(patternPhotoDto);
        }
        return patternPhotoDtos;
    }

    public List<DiscussionPhotoDto> copyDiscussionPhotosToDto(Collection<DiscussionPhoto> photos){
        List<DiscussionPhotoDto> discussionPhotoDtos=new ArrayList<>();
        for(DiscussionPhoto photo:photos){
            DiscussionPhotoDto discussionPhotoDto=new DiscussionPhotoDto(photo.getId(),photo.getFilename(), photo.getFileType(),photo.getFileContent());
            discussionPhotoDtos.add(discussionPhotoDto);
        }
        return discussionPhotoDtos;
    }
    public void createPhoto(String filename,byte[] filecontent,String filetype, Pattern pattern){
        LOG.info("createPatternPhoto");
        PatternPhoto photo=new PatternPhoto();
        photo.setFilename(filename);
        photo.setFileContent(filecontent);
        photo.setFileType(filetype);
        if(photo.getFileContent().length != 0) {
            pattern.getPhotos().add(photo);
            photo.setPattern(pattern);
            entityManager.persist(photo);
        }
    }

    public void createPhoto(String filename,byte[] filecontent,String filetype, Discussion discussion){
        LOG.info("createPatternPhoto");
        DiscussionPhoto photo=new DiscussionPhoto();
        photo.setFilename(filename);
        photo.setFileContent(filecontent);
        photo.setFileType(filetype);
        if(photo.getFileContent().length != 0) {
            discussion.getPhotos().add(photo);
            photo.setDiscussion(discussion);
            entityManager.persist(photo);
        }
    }

    public PatternPhotoDto findPatternPhotosById(long id) {
        PatternPhoto photo = entityManager
                .createQuery("SELECT p FROM PatternPhoto p where p.id = :id", PatternPhoto.class)
                .setParameter("id", id)
                .getSingleResult();
        Pattern pattern=entityManager.find(Pattern.class, photo.getPattern().getId());
        PatternPhotoDto patternPhotoDto=new PatternPhotoDto(photo.getId(),photo.getFilename(), photo.getFileType(),photo.getFileContent());
        return patternPhotoDto;
    }

    public DiscussionPhotoDto findDiscussionPhotosById(long id) {
        DiscussionPhoto photo = entityManager
                .createQuery("SELECT p FROM DiscussionPhoto p where p.id = :id", DiscussionPhoto.class)
                .setParameter("id", id)
                .getSingleResult();
        Discussion discussion=entityManager.find(Discussion.class,photo.getDiscussion().getId());
        DiscussionPhotoDto discussionPhotoDto=new DiscussionPhotoDto(photo.getId(),photo.getFilename(), photo.getFileType(),photo.getFileContent());
        return discussionPhotoDto;
    }
}
