package model.entity;

import java.io.Serializable;

public class User extends Entity implements Serializable {
    private String email;
    private String firstName;
    private String secondName;
    private String phone;
    private String documentInfo;
    private String password;
    private Role role;
    private boolean blocked;

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
    public boolean isBlocked() {
        return blocked;
    }

    //Constructors
    public User(long ID, String email, String firstName, String secondName, String phone, String documentInfo, String password, Role role, boolean blocked) {
        this.ID = ID;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
        this.documentInfo = documentInfo;
        this.password = password;
        this.role = role;
        this.blocked = blocked;
    }

    enum Role{
        ADMIN,
        MANAGER,
        CLIENT,
        UNDEFINED;

        @Override
        public String toString() {
            switch (this){
                case ADMIN: return "admin";
                case CLIENT: return "client";
                case MANAGER: return "manager";
                default: return "undefined";
            }
        }
        public static Role getValueByName(String name){
            Role valueToReturn;
            if (name==null) return UNDEFINED;

            switch (name){
                case "admin": valueToReturn = ADMIN; break;
                case "client": valueToReturn = CLIENT; break;
                case "manager": valueToReturn = MANAGER; break;
                default:valueToReturn = UNDEFINED;
            }
            return valueToReturn;
        }
    }

}
