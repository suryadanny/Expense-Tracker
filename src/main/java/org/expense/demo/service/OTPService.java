package org.expense.demo.service;

import java.util.Properties;
import java.util.UUID;

//import java.util.Properties;

//import javax.ws.rs.*;
//import javax.ws.rs.core.*;

import org.expense.demo.model.User;
import org.expense.demo.repo.CredentialRepository;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class OTPService {
	
	private CredentialRepository credRepo = new CredentialRepository();
    
	public void sendOTPViaEmail(User user) {
        String fromEmail = "expense.tracker.cse606@gmail.com";
        String password = "hqzxowqahpmvndce";
        String toEmail = user.getEmail();
        String subject = "OTP TO UPDATE PASSWORD";
        String messageBody = "OTP to reset account - ";

        // Set up mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
       
        String OTP = UUID.randomUUID().toString().substring(0, 6);
        messageBody = messageBody.concat(OTP);
        try {
        	credRepo.insertOTP(user.getId(), OTP);
        }catch (Exception ex) {
        	System.out.println("exception occurred while saving otp : " +ex.getCause().getLocalizedMessage());
        	return;
        }
        // Create a Session object with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // Create a message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(messageBody);

            // Send the message
            Transport.send(message);
            System.out.println("Email sent successfully! : "+ toEmail +" - message "+messageBody);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
}
