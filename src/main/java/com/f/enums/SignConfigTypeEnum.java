package com.f.enums;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rebysfu@gmail.com
 * @description：签名配置类型枚举
 * @create 2019-01-22 上午11:03
 **/
public enum SignConfigTypeEnum {
    CHANNEL("0", "渠道配置"),
    REQUEST("1", "单个请求配置"),
    CALLBACK("2", "回调配置");
    @Getter
    @Setter
    String code;
    @Getter
    @Setter
    String name;

    SignConfigTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private static Map<String, String> map = new HashMap<String, String>(SignConfigTypeEnum.values().length);

    static {
        for (SignConfigTypeEnum signConfigTypeEnum : SignConfigTypeEnum.values()) {
            map.put(signConfigTypeEnum.getCode(), signConfigTypeEnum.getName());
        }
    }

    public static Map<String, String> getSignConfigTypeEnumMap() {
        return map;
    }

    public static boolean isValidcode(String code) {
        for (SignConfigTypeEnum signConfigTypeEnum : SignConfigTypeEnum.values()) {
            if (signConfigTypeEnum.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }
}
