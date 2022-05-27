package com.epam.tkach.carrent.controller.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface CryptographyI {
    PassGenerationResult generateStrongPasswordHash(String originalPassword, byte[] inputSalt) throws NoSuchAlgorithmException, InvalidKeySpecException;

    boolean passIsCorrect(String notHashedPass, byte[] salt, String hashedPass);
}
