package com.patterns.designpatternplatform.abstractClasses;

import com.patterns.designpatternplatform.entities.Discussion;
import com.patterns.designpatternplatform.entities.Pattern;
import com.patterns.designpatternplatform.entities.User;

import javax.swing.text.html.parser.Entity;

public abstract class ReviewTemplate {

    public boolean recordSucces;

    public final void reviewPost(int review, long userId, long postId) {// validate input, add to database and determine public opinion based on average rating
        validateInput(review);
        recordRating(review, userId, postId);
        calculateSentiment(averageRating(postId),postId);
    }

    public abstract void validateInput(int review); // check if the review input is valid

    public abstract void recordRating(int review, long userId, long postId); // add rating to database

    public abstract double averageRating(long postId); // calculate average rating

    public abstract void calculateSentiment(double averageRating,long postId);// determine public opinion based on average rating

    public boolean isAlreadySubmitted() {
        return recordSucces;
    }

    public void setRecordSucces(boolean recordSucces) {
        this.recordSucces = recordSucces;
    }
}