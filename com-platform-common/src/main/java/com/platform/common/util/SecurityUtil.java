package com.platform.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Huangyonghao on 2019/8/14 10:58.
 */
public class SecurityUtil {

    private static final byte[] DES_KEY = new byte[]{0x75, 0x2c, 0x54, (byte) 0x9d, 0x3b, 0x6b, 0x57, (byte) 0x86};

    public static String desEncrypt(String str) throws Exception {

        Cipher ecipher = Cipher.getInstance("DES");
        ecipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(DES_KEY, 0, DES_KEY.length, "DES"));
        return new sun.misc.BASE64Encoder().encode(ecipher.doFinal(str.getBytes("UTF-8")));

    }

    public static String desDecrypt(String str) throws Exception {

        Cipher dcipher = Cipher.getInstance("DES");
        dcipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(DES_KEY, 0, DES_KEY.length, "DES"));
        return new String(dcipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(str)), "UTF8");

    }

    public static void main(String[] args) {
        try {

        }catch (Exception ex){

        }
    }
}
