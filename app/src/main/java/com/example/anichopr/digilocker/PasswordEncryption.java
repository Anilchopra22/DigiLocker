package com.example.anichopr.digilocker;

/**
 * Created by anichopr on 7/27/2015.
 */
public class PasswordEncryption {
    public static String GetEncryptedPassword(String password, String seed) {
        String intermediatePassword = MD5.calcMD5(password).toUpperCase();
        String seedWithPassword = seed + intermediatePassword;
        String encryptedPassword = MD5.calcMD5(seedWithPassword);
        return encryptedPassword.toUpperCase();
    }
}
