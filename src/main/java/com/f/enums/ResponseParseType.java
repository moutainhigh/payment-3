package com.f.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : caibi
 * @date : 2019-01-15 14:48
 * 请求response解析方式
 */
public enum ResponseParseType {

    REGEX("正则表达式"),
    SPEL("spel解析 json等"),
    HTML("html解析"),
    XML("xml解析"),

    ;

    private static Map<Integer, ResponseParseType> maps = new HashMap<>();
    private static Map<Integer, String> ordinal2Desc = new HashMap<>();

    static{
        for(ResponseParseType type : ResponseParseType.values()){
            maps.put(type.ordinal(), type);
            ordinal2Desc.put(type.ordinal(), type.description);
        }
    }

    private String description;

    private ResponseParseType(String desc){
        this.description = desc;
    }

    public String getDescription(){
        return description;
    }

    public static ResponseParseType valueOf(int ordinal){
        ResponseParseType result = maps.get(ordinal);
        return result;
    }

    public static Map<Integer, String> getOrdinal2Desc(){
        return ordinal2Desc;
    }
}
