package com.dmitriyevseyev.carWeb.shared.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashGenerator {
    private static PasswordHashGenerator instance;

    public static PasswordHashGenerator getInstance() {
        if (instance == null) {
            instance = new PasswordHashGenerator();
        }
        return instance;
    }

    public PasswordHashGenerator() {
    }

    public String getHashPassword(String pass) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("MessageDigestError. " + e.getMessage());
        }

        byte[] passArr = pass.getBytes(StandardCharsets.UTF_8);

        byte[] md5Arr = md.digest(passArr);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < md5Arr.length; i++) {
            if ((0xff & md5Arr[i]) < 0x10) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(0xff & md5Arr[i]));
        }
        String hash = sb.toString();
        return hash;
    }
}
