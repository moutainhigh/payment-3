package com.f.utils;

import org.springframework.util.Base64Utils;

/**
 * Created by damai on 24/01/18.
 */
public class MyBase64Utils {
    public static byte[] decode(String privateKey) {
        return Base64Utils.decodeFromString(privateKey);
    }

    public static String encode(byte[] sign) {
        return Base64Utils.encodeToString(sign);
    }
}
