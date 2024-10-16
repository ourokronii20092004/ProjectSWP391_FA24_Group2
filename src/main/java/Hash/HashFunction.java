/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package Hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 *
 * @author Le Trung Hau - CE180481
 */
public class HashFunction {

    private static final int SALT_LENGTH = 32;
    private static final int ITERATION_COUNT = 500_000;
    private static final String HASH_ALGORITHM = "SHA-512";

    /**
     * Hashes the provided password with a salt using PBKDF2 With Hmac SHA512.
     *
     * @param password  The password to hash.
     * @param salt      The salt to use.
     * @return          The Base64 encoded hash of the password with the salt.
     */
    public static String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(salt);
            byte[] hash = md.digest(password.getBytes());
            for (int i = 0; i < ITERATION_COUNT - 1; i++) {
                hash = md.digest(hash);
            }
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Generates a secure random salt.
     *
     * @return A randomly generated salt.
     */
    public static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    /**
     * Compares two byte arrays for equality in a time-constant manner.
     * Protects against timing attacks.
     *
     * @param a The first byte array.
     * @param b The second byte array.
     * @return True if the arrays are equal, false otherwise.
     */
    public static boolean slowEquals(byte[] a, byte[] b) {
        if (a == null || b == null) {
            return false;
        }
        int diff = a.length ^ b.length;
        for (int i = 0; i < Math.min(a.length, b.length); i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }
    
    /**
     * Compares a user's input password to a stored password hash using the provided salt.
     *
     * @param input The user's input password.
     * @param salt The salt used to hash the stored password.
     * @param storedPasswordHash The Base64-encoded hash of the stored password.
     * @return True if the input password matches the stored password hash, false otherwise.
     */
    public static boolean comparePasswords(String input, String salt, String storedPasswordHash) {
        // Decode the Base64-encoded salt
        byte[] saltBytes = Base64.getDecoder().decode(salt);

        // Hash the input password using the same salt
        String inputPasswordHash = hashPassword(input, saltBytes);

        // Compare the hashed input password to the stored password hash
        return slowEquals(inputPasswordHash.getBytes(), storedPasswordHash.getBytes());
    }
}