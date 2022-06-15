package com.epam.tkach.carrent.model.service;

import com.epam.tkach.carrent.model.email.EmailInformation;
import com.epam.tkach.carrent.model.email.EmailNotification;
import com.epam.tkach.carrent.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Properties;


public class NotificationService {
    private static final Logger logger = LogManager.getLogger(NotificationService.class);
    //Sends notification about top up
    public static void notifyAboutTopUp(User user, String locale, double amount){
        if (user.isReceiveNotifications()){
            EmailNotification notification = new EmailNotification();
            EmailInformation info = new EmailInformation();
            info.setRecipient(user.getEmail());
            info.setTheme(getLocaleText(locale, "theme.topUp"));
            String formattedDouble = new DecimalFormat("#0.00").format(amount);
            info.setText(String.format(getLocaleText(locale, "text.topUp"),formattedDouble));

            notification.sendNotification(info);
        }
    }
    public static void notifyAboutRegistration(String email, String locale,  boolean sendNotifications){
        if (sendNotifications){
            EmailInformation info = new EmailInformation();
            info.setRecipient(email);
            info.setTheme(getLocaleText(locale, "theme.register"));
            info.setText(getLocaleText(locale, "text.register"));
        }
    }
    //public
    private static String getLocaleText(String locale, String key){
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String messagesPath;
        if ("en".equals(locale)) {
            messagesPath = rootPath + "email_messages_en.properties";
        }else{
            messagesPath = rootPath + "email_messages_ua.properties";
        }

        try {
            Properties messages = new Properties();
            messages.load(new FileReader(messagesPath));
            return (String) messages.get(key);
        } catch (IOException e) {
            logger.error(e);
            return "";
        }
    }
}
