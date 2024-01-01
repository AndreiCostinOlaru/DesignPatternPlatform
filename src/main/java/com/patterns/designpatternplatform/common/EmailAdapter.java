package com.patterns.designpatternplatform.common;

import com.patterns.designpatternplatform.interfaces.Notification;

public class EmailAdapter implements Notification {
    private final JavaMailSender javaMailSender;

    public EmailAdapter() {
        this.javaMailSender = JavaMailSender.getInstance();
    }

    @Override
    public void sendNotification(String recipient, String subject, String message) {
        String username = "raid95885@gmail.com";
        String password = "rlvssdfllbxeirxn";
        javaMailSender.sendEmail(username, password, recipient, subject, message);
    }
}
