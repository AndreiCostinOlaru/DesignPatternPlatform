package com.patterns.designpatternplatform.common;

import com.patterns.designpatternplatform.abstractClasses.ReviewTemplate;
import com.patterns.designpatternplatform.entities.*;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@Stateless
public class LikeDislikeReview extends ReviewTemplate {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public void validateInput(int review) {
        if (review != 0 && review != -1) {
            throw new IllegalArgumentException("Input should be either 0 (like) or -1 (dislike).");
        }
    }
    @Transactional
    @Override
    public void recordRating(int review, long userId, long postId) {
        Discussion discussion=entityManager.find(Discussion.class, postId);
        User user=entityManager.find(User.class, userId);
        boolean hasSubmittedReview = user.getDiscussionReviews()
                .stream()
                .anyMatch(discussionReview -> discussionReview.getDiscussion().getId() == postId);
        if(!hasSubmittedReview){
        DiscussionReview discussionReview=new DiscussionReview();
        discussionReview.setDiscussion(discussion);
        discussionReview.setUser(user);
        discussionReview.setReview(review);
        user.getDiscussionReviews().add(discussionReview);
        discussion.getReviews().add(discussionReview);
        entityManager.persist(discussionReview); }
        recordSucces=hasSubmittedReview;
    }

    @Override
    public double averageRating(long postId) {
        try {
            TypedQuery<DiscussionReview> typedQuery = entityManager.createQuery("SELECT c FROM DiscussionReview c WHERE c.discussion.id=:discussionId", DiscussionReview.class);
            typedQuery.setParameter("discussionId",postId);
            List<DiscussionReview> reviews = typedQuery.getResultList();
            double averageRating=0;
            for (DiscussionReview review:reviews) {
                if(review.getReview()==0){
                    averageRating++;
                }
                else{
                    averageRating--;
                }
            }
            return averageRating;
        }
        catch (Exception e){
            throw new EJBException(e);
        }
    }

    @Override
    public void calculateSentiment(double averageRating,long postId) {
        Discussion discussion=entityManager.find(Discussion.class,postId);
        if (averageRating > 0) {
            discussion.setPublicOpinion("Positive");
        } else if (averageRating < 0) {
            discussion.setPublicOpinion("Negative");
        } else {
            discussion.setPublicOpinion("Mixed");
        }
    }
}
