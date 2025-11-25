package com.warpg.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Secure password hashing utility
 * Uses SHA-256 with salt for password security
 */
public class PasswordUtil {
    
    private static final int SALT_LENGTH = 16;
    private static final SecureRandom random = new SecureRandom();
    
    /**
     * Hash a password with a generated salt
     * @param password Plain text password
     * @return Salted hash in format: salt:hash
     */
    public static String hashPassword(String password) {
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        
        byte[] hash = hash(password, salt);
        
        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashBase64 = Base64.getEncoder().encodeToString(hash);
        
        return saltBase64 + ":" + hashBase64;
    }
    
    /**
     * Verify a password against a stored hash
     * @param password Plain text password to verify
     * @param storedHash Stored hash in format salt:hash
     * @return true if password matches
     */
    public static boolean verifyPassword(String password, String storedHash) {
        String[] parts = storedHash.split(":");
        if (parts.length != 2) {
            return false;
        }
        
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] expectedHash = Base64.getDecoder().decode(parts[1]);
        
        byte[] actualHash = hash(password, salt);
        
        return MessageDigest.isEqual(expectedHash, actualHash);
    }
    
    /**
     * Hash a password with a given salt using SHA-256
     */
    private static byte[] hash(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            return md.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}
