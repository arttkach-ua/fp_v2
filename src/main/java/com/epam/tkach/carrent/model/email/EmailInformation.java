package com.epam.tkach.carrent.model.email;

public class EmailInformation {
    private String recipient;
    private String theme;
    private String text;
    //Getters
    public String getRecipient() {
        return recipient;
    }

    public String getTheme() {
        return theme;
    }

    public String getText() {
        return text;
    }
    //Setters
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }
    public void setText(String text) {
        this.text = text;
    }
    //Constructor
    public EmailInformation() {
    }
}
