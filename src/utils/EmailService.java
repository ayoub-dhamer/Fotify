/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fares
 */
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {

    public static void sendMailFunc(String emailTo, String object, String mssg) {
        ExecutorService myExecutor = Executors.newCachedThreadPool();

        myExecutor.execute(new Runnable() {
            public void run() {
                final String user = "stacksauqd@gmail.com";
                final String password = "Fares123##";

                String to = emailTo;

                String host = "smtp.gmail.com";

                // Get system properties
                Properties properties = System.getProperties();

                // Setup mail server
                properties.put("mail.smtp.host", host);
                properties.put("mail.smtp.port", "465");
                properties.put("mail.smtp.ssl.enable", "true");
                properties.put("mail.smtp.auth", "true");

                //Get the session object  
                Session session = Session.getDefaultInstance(properties,
                        new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

                //Compose the message  
                try {
                    MimeMessage message = new MimeMessage(session);

                    message.setFrom(new InternetAddress(user));

                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                    message.setSubject(object);
                    message.setText(mssg);

                    //send the message  
                    Transport.send(message);

                    System.out.println("message sent successfully...");
                } catch (AddressException ex) {
                    Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MessagingException ex) {
                    Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
        });

    }
}
