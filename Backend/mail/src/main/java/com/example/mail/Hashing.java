package com.example.mail;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
    public static String hashingPassword(String string) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashedString = messageDigest.digest(string.getBytes(StandardCharsets.UTF_8));
        BigInteger bigInteger = new BigInteger(1,hashedString);
        StringBuilder hexString = new StringBuilder(bigInteger.toString());
        while(hexString.length()<64){
            hexString.insert(0,'0');
        }
        return hexString.toString();
    }
}
