/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

/**
 *
 * @author CE181515 - Phan Viet Phat
 */
public class SendEmail {

    private final String email = "pambshop24@gmail.com";
    private final String password = "lisjqdmuimgbikts";

    public void sendEmail(String cus_email, String subject, String body) {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };

        Session session = Session.getInstance(properties, auth);
        Message message = new MimeMessage(session);
        try {
            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(cus_email, false));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendConfirmEmailForm(Models.User user) {
        String body = "";
        body += "Dear " + user.getUserName() + "\n";
        body += "Thank you for trusting and using our website!\n";
        body += "Please click the link below to confirm your email:\n";
        body += "http://localhost:8080/RegisterController/Confirm/" + user.getPassword() + "?username=" + user.getUserName();
        body += "\n";
        body += "Hope you have a great day!\n";
        body += "PAMB.";
        sendEmail(user.getEmail(), "Confirm your email!", body);
    }
}
