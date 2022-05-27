package com.epam.tkach.carrent.controller.security;

/**
 * Class is user to return result of password generation
 * contains Fields: salt and pass(hashed pass)
 */
public class PassGenerationResult {
    private byte[] salt;
    private String generatedPassHash;
    //Constructors
    public PassGenerationResult(byte[] salt, String generatedPassHash) {
        this.salt = salt;
        this.generatedPassHash = generatedPassHash;
    }
    public PassGenerationResult() {
    }

    //Getters
    public byte[] getSalt() {
        return salt;
    }
    public String getGeneratedPassHash() {
        return generatedPassHash;
    }

    //Setters
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
    public void setGeneratedPassHash(String generatedPassHash) {
        this.generatedPassHash = generatedPassHash;
    }
}
