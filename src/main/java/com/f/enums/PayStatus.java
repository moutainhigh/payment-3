package com.f.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rebysfu@gmail.com
 * @description：支付状态
 * @create 2019-01-14 上午10:22
 **/
public enum PayStatus {
    /**
     * 提现相关状态
     */
    PAY_WAIT("0", "待提现"),
    PAY_CHECKING("1", "提现中"),
    PAY_SUCCESS("2", "成功"),
    PAY_FAIL("3", "失败");
    private String value;
    private String name;

    PayStatus(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static PayStatus getPayStatus(String status) {
        for (PayStatus it : PayStatus.values()) {
            if (it.getValue() .equals(status)) {
                return it;
            }
        }
        return null;
    }

    private static Map<String, String> map = new HashMap<String, String>(PayStatus.values().length);

    static {
        for (PayStatus payStatus : PayStatus.values()) {
            map.put(payStatus.getValue(), payStatus.getName());
        }
    }

    public static Map<String, String> getPayStatusMap() {
        return map;
    }
}
