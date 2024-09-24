package convenience.email;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class Email {
    public static void sendMessage(String text, String emailAddress) throws MessagingException {
        System.out.println(emailAddress + "convenience/email");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");
        String userName = "akhmadjon2122@gmail.com";
        String password = "wqhsthybzmloiszy";
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                System.out.println("Authentication...");
                return new PasswordAuthentication(userName, password);
            }
        });
        Message message = new MimeMessage(session);
        message.setSubject("Asslomu aleykum");
        message.setText(text);
        System.out.println(text + " nomeri");
        message.setFrom(new InternetAddress(userName));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));  // Corrected here
        System.out.println("Sending...");
        Transport.send(message);
        System.out.println("Sent successfully!");
    }
}
