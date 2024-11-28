package com.zh.look.IDCardLock;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class IDCardEncryptor {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY = "shdjsidkxoaksosd"; // 确保KEY长度为16字节
    private static final String IV = "oejxkspcnasjdkss";   // 确保IV长度为16字节

    public  String encryptIDCard(String idCard) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] encrypted = cipher.doFinal(idCard.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public  String decryptIDCard(String encryptedIDCard) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] decoded = Base64.getDecoder().decode(encryptedIDCard);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
