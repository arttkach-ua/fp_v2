package com.epam.tkach.carrent.model.email;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class EmailNotification {
    private String userName;
    private String passWord;
    private static final Properties properties = new Properties();
    private static final Logger logger = LogManager.getLogger(EmailNotification.class);

    public EmailNotification() {
        try {
            String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            String emailConfigPath = rootPath + "email.properties";
            properties.load(new FileReader(emailConfigPath));

            userName = (String) properties.get("e-mail");
            passWord = (String) properties.get("password");
        } catch (IOException e) {
            logger.debug("Notification init error");
            logger.debug(e);
        }
    }

    public void sendNotification(EmailInformation info){
        logger.debug("to userName =" + info.getRecipient());

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, passWord);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(info.getRecipient())
            );
            message.setSubject(info.getTheme());
            message.setText(info.getText());

            Transport.send(message);

            logger.debug("Email send success");

        } catch (MessagingException e) {
            logger.error(e);
        }
    }
}