package com.epam.tkach.carrent.controller.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface CryptographyI {
    String generateStrongPasswordHash(String originalPassword) throws NoSuchAlgorithmException, InvalidKeySpecException;

    boolean passIsCorrect(String notHashedPass, String hashedPass);
}
