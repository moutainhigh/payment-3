package com.f.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rebysfu@gmail.com
 * @description：签名方式
 * @create 2019-01-14 下午12:16
 **/
public enum SignType {
    MD5("0", "MD5签名"),
    RSA2("1", "RSA2签名");
    private String value;
    private String name;

    SignType(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static SignType getSignTypes(String value) {
        for (SignType it : SignType.values()) {
            if (it.getValue() .equals(value)) {
                return it;
            }
        }
        return null;
    }

    private static Map<String, String> map = new HashMap<String, String>(SignType.values().length);

    static {
        for (SignType signType : SignType.values()) {
            map.put(signType.getValue(), signType.getName());
        }
    }

    public static Map<String, String> getSignTypeMap() {
        return map;
    }

    public static boolean isValidcode(String code) {
        for (SignType signType : SignType.values()) {
            if (signType.getValue().equals(code)) {
                return true;
            }
        }
        return false;
    }
}
