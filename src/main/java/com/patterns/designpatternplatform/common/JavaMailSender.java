package com.patterns.designpatternplatform.common;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class JavaMailSender {

    private static JavaMailSender instance;

    private JavaMailSender() {
    }

    public static synchronized JavaMailSender getInstance() {
        if (instance == null) {
            instance = new JavaMailSender();
        }
        return instance;
    }

    public void sendEmail(String username, String password, String recipient, String subject, String message) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(username));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            emailMessage.setSubject(subject);
            emailMessage.setText(message);

            Transport.send(emailMessage);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}
