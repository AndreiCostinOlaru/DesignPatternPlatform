package com.patterns.designpatternplatform.interfaces;

public interface CommentSubject {
    void registerObserver(CommentObserver observer, long userId);

    void removeObserver(CommentObserver observer, long userId);

    void notifyCommentObservers(String reply, long userId,String replier);
}
