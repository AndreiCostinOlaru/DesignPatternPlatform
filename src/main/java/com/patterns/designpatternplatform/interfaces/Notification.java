package com.patterns.designpatternplatform.interfaces;

public interface Notification {
    void sendNotification(String recipient, String subject, String message);//sends a notification to the recipient with a subject and message
}
