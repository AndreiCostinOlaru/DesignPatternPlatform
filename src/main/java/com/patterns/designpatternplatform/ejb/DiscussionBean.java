package com.patterns.designpatternplatform.ejb;

import com.patterns.designpatternplatform.common.DiscussionDto;
import com.patterns.designpatternplatform.common.PatternDto;
import com.patterns.designpatternplatform.common.PatternPhotoDto;
import com.patterns.designpatternplatform.entities.*;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.security.DigestInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class DiscussionBean {
    private static final Logger LOG = Logger.getLogger(DiscussionBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    PhotoBean photoBean;

    public List<DiscussionDto> copyDiscussionsToDto(List<Discussion> discussions){
        List<DiscussionDto> discussionDtos=new ArrayList<>();
        for(Discussion discussion:discussions){
            DiscussionDto discussionDto=new DiscussionDto(discussion.getId(),discussion.getName(),discussion.getPattern(),discussion.getOwner().getUsername(),photoBean.copyDiscussionPhotosToDto(discussion.getPhotos()),discussion.getDescription(),discussion.getCode(), discussion.getPublicOpinion());
            discussionDtos.add(discussionDto);
        }
        return discussionDtos;
    }

    public List<DiscussionDto> findAllDiscussions(){
        try {
            LOG.info("findAllDiscussions");
            TypedQuery<Discussion> typedQuery = entityManager.createQuery("SELECT c FROM Discussion c", Discussion.class);
            List<Discussion> discussions = typedQuery.getResultList();
            return copyDiscussionsToDto(discussions);
        }
        catch (Exception e){
            throw new EJBException(e);
        }
    }

    @Transactional
    public Discussion createDiscussion(String name, long userId,long patternId,String description,String code){
        LOG.info("createDiscussion");

        Discussion discussion=new Discussion();
        discussion.setName(name);
        discussion.setDescription(description);
        discussion.setCode(code);

        Pattern pattern=entityManager.find(Pattern.class, patternId);
        pattern.getDiscussions().add(discussion);
        discussion.setPattern(pattern);

        User user=entityManager.find(User.class, userId);
        user.getDiscussions().add(discussion);
        discussion.setOwner(user);
        entityManager.persist(discussion);
        return discussion;
    }


    public DiscussionDto findById(Long discussionId) {
        TypedQuery<Discussion> typedQuery = entityManager.createQuery("SELECT d FROM Discussion d WHERE d.id = :discussionId", Discussion.class);
        typedQuery.setParameter("discussionId", discussionId);
        Discussion discussion = typedQuery.getSingleResult();
        entityManager.refresh(discussion);
        DiscussionDto discussionDto=new DiscussionDto(discussion.getId(),discussion.getName(),discussion.getPattern(),discussion.getOwner().getUsername(),photoBean.copyDiscussionPhotosToDto(discussion.getPhotos()),discussion.getDescription(),discussion.getCode(), discussion.getPublicOpinion());
        return discussionDto;
    }

    public void updateDiscussion(String name,long discussionId, String description, String code, long patternId) {
        LOG.info("updateDiscussion");
        Discussion discussion=entityManager.find(Discussion.class,discussionId);
        discussion.setName(name);
        discussion.setDescription(description);
        discussion.setCode(code);
        Pattern oldPattern=discussion.getPattern();
        oldPattern.getDiscussions().remove(discussion);
        Pattern pattern=entityManager.find(Pattern.class,patternId);
        pattern.getDiscussions().add(discussion);
        discussion.setPattern(pattern);
    }

    public void deleteDiscussionsByIds(List<Long> discussionsIds) {
        LOG.info("deleteDiscussionsByIds");

        for(Long id:discussionsIds){
            Discussion discussion=entityManager.find(Discussion.class,id);
            for (DiscussionPhoto discussionPhoto:discussion.getPhotos()) {
                entityManager.remove(discussionPhoto);
            }
            for (DiscussionReview discussionReview : discussion.getReviews()) {
                entityManager.remove(discussionReview);
            }
            for (Comment comment:discussion.getComments()) {
                entityManager.remove(comment);
            }
            entityManager.remove(discussion);
        }
    }
}
