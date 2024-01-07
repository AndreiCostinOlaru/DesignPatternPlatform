package com.patterns.designpatternplatform.common;

import com.patterns.designpatternplatform.interfaces.Notification;

public class EmailAdapter implements Notification {
    private final JavaMailSender javaMailSender;

    public EmailAdapter() {
        String username = "raid95885@gmail.com";
        String password = "rlvssdfllbxeirxn";
        this.javaMailSender = JavaMailSender.getInstance(username,password);
    }

    @Override
    public void sendNotification(String recipient, String subject, String message) {
        javaMailSender.sendEmail(recipient, subject, message);
    }
}
