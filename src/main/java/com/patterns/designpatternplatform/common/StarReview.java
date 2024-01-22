package com.patterns.designpatternplatform.common;

import com.patterns.designpatternplatform.abstractClasses.ReviewTemplate;
import com.patterns.designpatternplatform.entities.Pattern;
import com.patterns.designpatternplatform.entities.PatternReview;
import com.patterns.designpatternplatform.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@Stateless
public class StarReview extends ReviewTemplate {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public void validateInput(int review) { //checks if the review input is valid
        if (review < 1 || review > 5) {
            throw new IllegalArgumentException("Stars should be between 1 and 5.");
        }
    }
    @Transactional
    @Override
    public void recordRating(int review, long userId, long postId) {//checks if the user has already submitted a review on the pattern and, if not, adds the review to the database
        Pattern pattern=entityManager.find(Pattern.class, postId);
        User user=entityManager.find(User.class, userId);
        boolean hasSubmittedReview = user.getPatternReviews()
                .stream()
                .anyMatch(patternReview -> patternReview.getPattern().getId() == postId);
        if(!hasSubmittedReview) {
            PatternReview patternReview = new PatternReview();
            patternReview.setPattern(pattern);
            patternReview.setUser(user);
            patternReview.setReview(review);
            user.getPatternReviews().add(patternReview);
            pattern.getReviews().add(patternReview);
            entityManager.persist(patternReview);
        }
        setRecordSucces(hasSubmittedReview);
    }

    @Override
    public double averageRating(long postId) {//calculates average rating based on the number of stars
        try {
            TypedQuery<PatternReview> typedQuery = entityManager.createQuery("SELECT c FROM PatternReview c WHERE c.pattern.id=:patternId", PatternReview.class);
            typedQuery.setParameter("patternId",postId);
            List<PatternReview> reviews = typedQuery.getResultList();
            double averageRating=0;
            for (PatternReview review:reviews) {
                averageRating+=review.getReview();
            }
            averageRating=averageRating/reviews.size();
            return averageRating;
        }
        catch (Exception e){
            throw new EJBException(e);
        }
    }

    @Override
    public void calculateSentiment(double averageRating, long postId) {// determines public opinion based on the average rating
        Pattern pattern=entityManager.find(Pattern.class,postId);
        if (averageRating >= 4.0) {
            pattern.setPublicOpinion("Positive");
        } else if (averageRating >= 2.0) {
            pattern.setPublicOpinion("Mixed");
        } else {
            pattern.setPublicOpinion("Negative");
        }
    }
}
