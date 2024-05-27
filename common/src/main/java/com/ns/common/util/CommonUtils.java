package com.ns.common.util;

import java.security.SecureRandom;

public class CommonUtils {

    // Define the length of the verification code
    private static final int CODE_LENGTH = 16;

    public static String generateFile(int codeLength) {
        // Define characters that can be used in the verification code
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        // Initialize SecureRandom for generating random numbers
        SecureRandom random = new SecureRandom();

        // Create a StringBuilder to store the generated code
        StringBuilder codeBuilder = new StringBuilder();

        // Generate random characters and append them to the codeBuilder until it reaches the specified length
        for (int i = 0; i < codeLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            codeBuilder.append(randomChar);
        }

        // Convert StringBuilder to String and return the generated verification code
        return codeBuilder.toString();
    }

    // Method to generate a random verification code
    public static String generateVerificationCode() {
        // Define characters that can be used in the verification code
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        // Initialize SecureRandom for generating random numbers
        SecureRandom random = new SecureRandom();

        // Create a StringBuilder to store the generated code
        StringBuilder codeBuilder = new StringBuilder();

        // Generate random characters and append them to the codeBuilder until it reaches the specified length
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            codeBuilder.append(randomChar);
        }

        // Convert StringBuilder to String and return the generated verification code
        return codeBuilder.toString();
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
