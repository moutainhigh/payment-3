package com.f.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : caibi
 * @date : 2019-01-15 14:58
 */
public enum RequestMethod {
    POST_JSON("application/json"),
    POST_FORM("application/x-www-form-urlencoded"),
    GET("GET"),
    ;

    private static Map<Integer, RequestMethod> maps = new HashMap<>();
    private static Map<Integer, String> ordinal2Desc = new HashMap<>();

    static {
        for (RequestMethod type : RequestMethod.values()) {
            maps.put(type.ordinal(), type);
            ordinal2Desc.put(type.ordinal(), type.description);
        }
    }

    private String description;

    private RequestMethod(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return description;
    }

    public static RequestMethod valueOf(int ordinal) {
        RequestMethod result = maps.get(ordinal);
        return result;
    }

    public static Map<Integer, String> getOrdinal2Desc() {
        return ordinal2Desc;
    }
}
