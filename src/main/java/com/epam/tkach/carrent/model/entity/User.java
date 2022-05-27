package com.epam.tkach.carrent.model.entity;

import com.epam.tkach.carrent.controller.Messages;
import com.epam.tkach.carrent.model.entity.enums.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class User extends Entity implements Serializable {
    @Email(message = Messages.INVALID_EMAIL)
    private String email;
    @Size(min = 2, message = Messages.ERROR_SHORT_FIRST_NAME)
    private String firstName;
    @Size(min = 2, message = Messages.ERROR_SHORT_SECOND_NAME)
    private String secondName;
    @Size(min = 2, message = Messages.INVALID_PHONE)
    private String phone;
    @Size(min = 2, message = Messages.INVALID_DOCUMENT)
    private String documentInfo;
    private String password;
    private Role role;
    private boolean blocked;
    private byte[] salt;


    //region Setters
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public void setDocumentInfo(String documentInfo) {
        this.documentInfo = documentInfo;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
    //Getters

    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getSecondName() {
        return secondName;
    }
    public String getDocumentInfo() {
        return documentInfo;
    }
    public Role getRole() {
        return role;
    }
    public boolean getBlocked() {
        return blocked;
    }
    public byte[] getSalt() { return salt; }

    //Constructors
    public User(int ID, String email, String firstName, String secondName, String phone, String documentInfo, String password, Role role, boolean blocked, byte[] salt) {
        this.ID = ID;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
        this.documentInfo = documentInfo;
        this.password = password;
        this.role = role;
        this.blocked = blocked;
        this.salt = salt;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", phone='" + phone + '\'' +
                ", documentInfo='" + documentInfo + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", blocked=" + blocked +
                ", ID=" + ID +
                ", salt=" + salt.toString() +
                '}';
    }
}
