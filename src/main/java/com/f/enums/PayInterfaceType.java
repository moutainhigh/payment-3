package com.f.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rebysfu@gmail.com
 * @description：接口类型  0 代付提现  1 账号余额查询  2 提现结果查询'
 * @create 2019-01-14 上午11:07
 **/
public enum PayInterfaceType {
    MERCHANT_WITHDRAW("0", "代付提现"),
    QUERY_ACCOUNT_BALANCE("1", "账号余额查询"),
    QUERY_WITHDRAWAL_RESULTS("2", "提现结果查询");
    private String value;
    private String name;

    PayInterfaceType(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static PayInterfaceType getPayInterfaceType(String status) {
        for (PayInterfaceType type : PayInterfaceType.values()) {
            if (type.getValue() == status) {
                return type;
            }
        }
        return null;
    }

    private static Map<String, String> map = new HashMap<String, String>(PayStatus.values().length);

    static {
        for (PayInterfaceType payInterfaceType : PayInterfaceType.values()) {
            map.put(payInterfaceType.getValue(), payInterfaceType.getName());
        }
    }

    public static Map<String, String> getPayInterfaceTypeMap() {
        return map;
    }

    public static boolean isValidcode(String value) {
        for (PayInterfaceType payInterfaceType : PayInterfaceType.values()) {
            if (payInterfaceType.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
