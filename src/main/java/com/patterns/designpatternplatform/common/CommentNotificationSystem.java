package com.patterns.designpatternplatform.common;

import com.patterns.designpatternplatform.interfaces.CommentObserver;
import com.patterns.designpatternplatform.interfaces.CommentSubject;

import java.util.*;

public class CommentNotificationSystem implements CommentSubject {
    private Map<Long, CommentObserver> observers;
    private Map<Long, List<String>> comments;

    public CommentNotificationSystem() {
        observers = new HashMap<>();
        comments = new HashMap<>();
    }
    @Override
    public void registerObserver(CommentObserver observer, long userId) {
        System.out.println("Registering " + observer + " with id: " + userId);
        if (!observers.containsKey(userId)) {
            System.out.println("Completely registered " + observer + " with id: " + userId);
            observers.put(userId, observer);
        } else {
            System.out.println("An observer is already registered with id: " + userId);
        }
    }

    @Override
    public void removeObserver(CommentObserver observer, long userId) {
        System.out.println("Removing " + observer + " with id: " + userId);
        CommentObserver registeredObserver = observers.get(userId);
        if (registeredObserver != null && registeredObserver.equals(observer)) {
            observers.remove(userId);
        } else {
            System.out.println("Observer with id: " + userId + " not found or does not match.");
        }
    }


    @Override
    public void notifyCommentObservers(String reply, long userId, String replier) {
        System.out.println("Notified " + userId + " because: " + replier + " replied " + reply);
        comments.computeIfAbsent(userId, k -> new ArrayList<>()).add(reply);
        CommentObserver userObserver = observers.get(userId);
        if (userObserver != null) {
            userObserver.update(reply, replier);
        }
    }
}
