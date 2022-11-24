package com.crackit.afyadaktari.service.password;


import org.springframework.beans.factory.annotation.Value;

public class Utils {

    @Value("${encrypt.key}")
    public static String encryptionKey;

    public static PlayfairCipher playfairCipher = new PlayfairCipher();

    public static String key = playfairCipher.generateKey(encryptionKey);

    public static String encrypt(String password) {
        return playfairCipher.encryptMsg(password, key);
    }

    public static String decrypt(String password) {
        return playfairCipher.encryptMsg(password, key);
    }
}
