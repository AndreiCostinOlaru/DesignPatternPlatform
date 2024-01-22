package com.patterns.designpatternplatform.ejb;

import com.patterns.designpatternplatform.common.PatternDto;
import com.patterns.designpatternplatform.common.PatternPhotoDto;
import com.patterns.designpatternplatform.common.UserDto;
import com.patterns.designpatternplatform.entities.*;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class PatternBean {
    private static final Logger LOG = Logger.getLogger(PatternBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    PhotoBean photoBean;

    public List<PatternDto> copyPatternsToDto(List<Pattern> patterns){//converts a list of patterns into a list of patternDtos
        List<PatternDto> patternDtos=new ArrayList<>();
        for(Pattern pattern:patterns){
            PatternDto patternDto=new PatternDto(pattern.getId(),pattern.getName(),pattern.getType(),pattern.getScope(),pattern.getOwner().getUsername(),photoBean.copyPatternPhotosToDto(pattern.getPhotos()),pattern.getDescription(),pattern.getCode(), pattern.getPublicOpinion());
            patternDtos.add(patternDto);
        }
        return patternDtos;
    }

    public List<PatternDto> findAllPatterns(){//returns a list of PatternDtos converted from a list of all the patterns from the database
        try {
            LOG.info("findAllPatterns");
            TypedQuery<Pattern> typedQuery = entityManager.createQuery("SELECT c FROM Pattern c", Pattern.class);
            List<Pattern> patterns = typedQuery.getResultList();
            return copyPatternsToDto(patterns);
        }
        catch (Exception e){
            throw new EJBException(e);
        }
    }

    @Transactional
    public Pattern createPattern(String name,String patternType,String patternScope, long id,String description,String code){ //adds a pattern to the database based on input
        LOG.info("createPattern");

        Pattern pattern=new Pattern();
        pattern.setName(name);
        pattern.setType(patternType);
        pattern.setScope(patternScope);
        pattern.setDescription(description);
        pattern.setCode(code);

        User user=entityManager.find(User.class, id);
        user.getPatterns().add(pattern);
        pattern.setOwner(user);
        entityManager.persist(pattern);
        return pattern;
    }


    public PatternDto findById(Long patternId) {//finds a pattern in the database based on id and converts it to a patternDto
        TypedQuery<Pattern> typedQuery = entityManager.createQuery("SELECT p FROM Pattern p WHERE p.id = :patternId", Pattern.class);
        typedQuery.setParameter("patternId", patternId);
        Pattern pattern = typedQuery.getSingleResult();
        entityManager.refresh(pattern);
        PatternDto patternDto=new PatternDto(pattern.getId(),pattern.getName(),pattern.getType(),pattern.getScope(),pattern.getOwner().getUsername(),photoBean.copyPatternPhotosToDto(pattern.getPhotos()),pattern.getDescription(),pattern.getCode(), pattern.getPublicOpinion());
        return patternDto;
    }

    public void updatePattern(String name, String type, String scope, long userId, long patternId, String description, String code) {//update the fields of a discussion in the database
        LOG.info("updatePattern");
        Pattern pattern=entityManager.find(Pattern.class,patternId);
        pattern.setName(name);
        pattern.setType(type);
        pattern.setScope(scope);
        pattern.setDescription(description);
        pattern.setCode(code);
        User oldUser= pattern.getOwner();
        oldUser.getPatterns().remove(pattern);

        User user= entityManager.find(User.class, userId);
        user.getPatterns().add(pattern);
        pattern.setOwner(user);
    }

    public void deletePatternsByIds(List<Long> patternIds) {//delete a list of patterns, alongside their reviews, discussions, and photos from the database based on id
        LOG.info("deletePatternsByIds");

        for (Long id : patternIds) {
            Pattern pattern = entityManager.find(Pattern.class, id);
            for (PatternReview patternReview : pattern.getReviews()) {
                entityManager.remove(patternReview);
            }
            for (Discussion discussion : pattern.getDiscussions()) {
                for (Comment comment : discussion.getComments()) {
                    entityManager.remove(comment);
                }
                for (DiscussionPhoto discussionPhoto : discussion.getPhotos()) {
                    entityManager.remove(discussionPhoto);
                }
                for (DiscussionReview discussionReview : discussion.getReviews()) {
                    entityManager.remove(discussionReview);
                }
                entityManager.remove(discussion);
            }
            entityManager.remove(pattern);
        }
    }
}
