package com.crackit.afyadaktari.service.password;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    @Value("${encrypt.key}")
    public static String encryptionKey;


    public static Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32,64,1,15*1024,2);
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
