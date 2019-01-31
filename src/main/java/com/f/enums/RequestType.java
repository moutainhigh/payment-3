package com.f.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : caibi
 * @date : 2019-01-15 14:54
 * 请求等类型
 */
public enum RequestType {

    DAIFU("代付提现"),
    CHECK_BALANCE("余额查询"),
    CHECK_DAIFU("提现结果查询"),
    ORDER("下单"),

    ;

    private static Map<Integer, RequestType> maps = new HashMap<>();
    private static Map<Integer, String> ordinal2Desc = new HashMap<>();

    static{
        for(RequestType type : RequestType.values()){
            maps.put(type.ordinal(), type);
            ordinal2Desc.put(type.ordinal(), type.description);
        }
    }

    private String description;

    private RequestType(String desc){
        this.description = desc;
    }

    public String getDescription(){
        return description;
    }

    public static RequestType valueOf(int ordinal){
        RequestType result = maps.get(ordinal);
        return result;
    }

    public static Map<Integer, String> getOrdinal2Desc(){
        return ordinal2Desc;
    }
}
