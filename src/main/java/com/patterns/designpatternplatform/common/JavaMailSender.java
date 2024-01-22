package com.patterns.designpatternplatform.common;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class JavaMailSender {

    private static JavaMailSender instance;
    private Session session;

    private JavaMailSender(String username, String password) {// sets up the necessary properties for connecting to a gmail smtp server and initializes the instance with the specified credentials
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.user", username);

        this.session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public static synchronized JavaMailSender getInstance(String username, String password) { // checks if instance was already initialized, if not it is initialized
        if (instance == null) {
            instance = new JavaMailSender(username, password);
        }
        return instance;
    }

    public void sendEmail(String recipient, String subject, String message) { // sends email to the specified recipient, having the specified subject and message
        try {
            Message emailMessage = new MimeMessage(this.session);
            String senderEmail = this.session.getProperties().getProperty("mail.smtp.user");
            emailMessage.setFrom(new InternetAddress(this.session.getProperties().getProperty("mail.smtp.user")));
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
