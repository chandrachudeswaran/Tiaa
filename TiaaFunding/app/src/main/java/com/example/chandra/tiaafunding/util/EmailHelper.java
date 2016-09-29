package com.example.chandra.tiaafunding.util;


import android.util.Log;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * Created by chandra on 9/25/2016.
 */
public class EmailHelper  {
    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;

    public void sendEmailMessage(String email,String subject,String message) throws AddressException, MessagingException {

        EmailHelper javaEmail = new EmailHelper();
        javaEmail.setMailServerProperties();
        javaEmail.createEmailMessage(email,subject,message);
        javaEmail.sendEmail();
    }

    public void setMailServerProperties(){

        String emailPort = "587";
        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");

    }

    public void createEmailMessage(String a,String b,String c) throws AddressException,MessagingException{
        String[] toEmails = {a};
        String emailSubject = b;
        String emailBody = c;

        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        for (int i = 0; i < toEmails.length; i++) {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
        }

        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html");
    }

    public void sendEmail() throws AddressException, MessagingException {

        String emailHost = "smtp.gmail.com";
        String fromUser = "tiaafunding";
        String fromUserEmailPassword = "tiaafunding12345";

        Transport transport = mailSession.getTransport("smtp");

        transport.connect(emailHost, fromUser, fromUserEmailPassword);
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
    }


}

