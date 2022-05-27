package com.epam.tkach.carrent.controller.security.implementation;

import com.epam.tkach.carrent.controller.security.CryptographyI;
import com.epam.tkach.carrent.controller.security.PassGenerationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class CryptographyPBKDF implements CryptographyI {
    private static final Logger logger = LogManager.getLogger(CryptographyPBKDF.class);
    @Override
    public PassGenerationResult generateStrongPasswordHash(String originalPassword, byte[] inputSalt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000;
        char[] chars = originalPassword.toCharArray();
        byte[] salt;
        if (inputSalt==null){
            salt = getSalt();
        }else {
            salt = inputSalt;
        }
        PassGenerationResult result = new PassGenerationResult();
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = skf.generateSecret(spec).getEncoded();
        result.setSalt(salt);
        String generatedHash = iterations + ":" + toHex(salt) + ":" + toHex(hash);

        result.setGeneratedPassHash(generatedHash);
        return result;
    }

    @Override
    public boolean passIsCorrect(String notHashedPass, byte[] salt, String hashedPass) {
        boolean isCorrect = false;
        try{
            isCorrect = generateStrongPasswordHash(notHashedPass, salt).getGeneratedPassHash().equals(hashedPass);
        } catch (NoSuchAlgorithmException|InvalidKeySpecException e) {
            logger.error(e);
        }
        return isCorrect;
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);

        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0".concat(Integer.toString(paddingLength)).concat("d"), 0) + hex;
        }else{
            return hex;
        }
    }
}
