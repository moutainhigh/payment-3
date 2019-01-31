package com.f.enums;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rebysfu@gmail.com
 * @description：支付类型
 * @create 2019-01-14 上午11:02
 **/
public enum PayTypeCode {
    WECHAT_PAY("1", "微信"), ALI_PAY("2", "支付宝");
    @Getter
    @Setter
    private String value;
    @Getter
    @Setter
    private String name;

    PayTypeCode(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static PayTypeCode getPayType(String id) {
        for (PayTypeCode payTypeCode : PayTypeCode.values()) {
            if (payTypeCode.getValue().equals(id)) {
                return payTypeCode;
            }
        }
        return null;
    }

    private static Map<String, String> map = new HashMap<String, String>(PayTypeCode.values().length);

    static {
        for (PayTypeCode signType : PayTypeCode.values()) {
            map.put(signType.getValue(), signType.getName());
        }
    }

    public static Map<String, String> getPayTypeCodeMap() {
        return map;
    }
}
