package com.f.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rebysfu@gmail.com
 * @description：金额单位
 * @create 2019-01-14 下午12:10
 **/
public enum AmtUnit {
    YUAN("0", "元"),
    FEN("1", "分");
    private String value;
    private String name;

    AmtUnit(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static AmtUnit getAmtUnits(String status) {
        for (AmtUnit it : AmtUnit.values()) {
            if (it.getValue() == status) {
                return it;
            }
        }
        return null;
    }

    private static Map<String, String> map = new HashMap<String, String>(AmtUnit.values().length);

    static {
        for (AmtUnit amtUnit : AmtUnit.values()) {
            map.put(amtUnit.getValue(), amtUnit.getName());
        }
    }

    public static Map<String, String> getAmtUnitMap() {
        return map;
    }

}
