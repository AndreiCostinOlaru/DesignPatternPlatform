package com.patterns.designpatternplatform.common;

import com.patterns.designpatternplatform.interfaces.Notification;

public class EmailAdapter implements Notification {
    private final JavaMailSender javaMailSender;

    public EmailAdapter() { //create JavaMailSender instance based on gmail credentials
        String username = "raid95885@gmail.com";
        String password = "rlvssdfllbxeirxn";
        this.javaMailSender = JavaMailSender.getInstance(username,password);
    }

    @Override
    public void sendNotification(String recipient, String subject, String message) { // send a message as email notification to the given recipient
        javaMailSender.sendEmail(recipient, subject, message);
    }
}
