package com.crackit.afyadaktari.service.password;

import org.springframework.beans.factory.annotation.Value;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    @Value("${encrypt.key}")
    public static String encryptionKey;
    public static String encrypt(String password)  throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter
                .printHexBinary(digest).toUpperCase();
    }
    public static Boolean passwordMatches(String password,String hash)throws NoSuchAlgorithmException{
        return encrypt(password).equals(hash);
    }

}
