package com.patterns.designpatternplatform.abstractClasses;

import com.patterns.designpatternplatform.entities.Discussion;
import com.patterns.designpatternplatform.entities.Pattern;
import com.patterns.designpatternplatform.entities.User;

import javax.swing.text.html.parser.Entity;

public abstract class ReviewTemplate {

    public boolean recordSucces;

    public final void reviewPost(int review, long userId, long postId) {
        validateInput(review);
        recordRating(review, userId, postId);
        calculateSentiment(averageRating(postId),postId);
    }

    public abstract void validateInput(int review);

    public abstract void recordRating(int review, long userId, long postId);

    public abstract double averageRating(long postId);

    public abstract void calculateSentiment(double averageRating,long postId);

    public boolean isAlreadySubmitted() {
        return recordSucces;
    }

    public void setRecordSucces(boolean recordSucces) {
        this.recordSucces = recordSucces;
    }
}