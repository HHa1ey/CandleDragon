package com.ha1ey.CandleDragon.common;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author Ha1ey
 * @descrition
 * @date 2023-08-01 11:27
 **/
public class WeaverVersionDecrypt {
    public  byte[] initsecretKey() throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed("WEAVERECOLOGYSECURITY3.0VERSION201607150857".getBytes());
        kg.init(128,sr);
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }

    public String decrypt(String str) {
        try {
            byte[] raw = initsecretKey();
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, secretKeySpec);
            byte[] encrypted1 = hex2byte(str);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "解密失败";
    }

    public  byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        } else {
            int l = strhex.length();
            if (l % 2 == 1) {
                return null;
            } else {
                byte[] b = new byte[l / 2];

                for(int i = 0; i != l / 2; ++i) {
                    b[i] = (byte)Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
                }
                return b;
            }
        }
    }

}
