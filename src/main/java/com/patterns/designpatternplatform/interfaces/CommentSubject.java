package com.patterns.designpatternplatform.interfaces;

public interface CommentSubject {
    void registerObserver(CommentObserver observer, long userId);//register user observer for comments based on id

    void removeObserver(CommentObserver observer, long userId);//removes user observer for comments based on id

    void notifyCommentObservers(String reply, long userId,String replier);//notifies the registered comment observer about a reply to a comment.
}
