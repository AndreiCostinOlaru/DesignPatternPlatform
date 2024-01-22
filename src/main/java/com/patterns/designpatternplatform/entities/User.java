package com.patterns.designpatternplatform.entities;

import com.patterns.designpatternplatform.common.EmailAdapter;
import com.patterns.designpatternplatform.interfaces.CommentObserver;
import com.patterns.designpatternplatform.interfaces.Notification;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class User implements CommentObserver{
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    private String username;

    @Basic
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private Collection<Pattern> patterns;
    private Collection<Discussion> discussions;
    private Collection<Comment> comments;
    private Collection<DiscussionReview> discussionReviews;
    private Collection<PatternReview> patternReviews;



    @OneToMany(mappedBy = "owner")
    public Collection<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(Collection<Discussion> discussions) {
        this.discussions = discussions;
    }

    @OneToMany(mappedBy = "owner")
    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "owner")
    public Collection<Pattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(Collection<Pattern> patterns) {
        this.patterns = patterns;
    }

    @OneToMany(mappedBy = "user")
    public Collection<DiscussionReview> getDiscussionReviews() {
        return discussionReviews;
    }

    public void setDiscussionReviews(Collection<DiscussionReview> discussionReviews) {
        this.discussionReviews = discussionReviews;
    }

    @OneToMany(mappedBy = "user")
    public Collection<PatternReview> getPatternReviews() {
        return patternReviews;
    }

    public void setPatternReviews(Collection<PatternReview> patternReviews) {
        this.patternReviews = patternReviews;
    }

    @Override
    public void update(String reply, String replier) {// user observer is updated by sending an email notification based on the input from the notification(reply and replier)
        Notification emailNotification = new EmailAdapter();
        emailNotification.sendNotification(this.getEmail(), "Design Pattern Platform: Reply to your comment!", replier+" replied to your comment with:"+ reply);
    }
}
