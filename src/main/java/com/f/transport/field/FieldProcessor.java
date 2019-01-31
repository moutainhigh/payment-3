package com.f.transport.field;

import com.f.enums.FieldType;
import com.f.transport.CallbackContext;
import com.f.transport.ParseResult;
import com.f.transport.RequestContext;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : caibi
 * @date : 2019-01-15 15:35
 */
public interface FieldProcessor {

    int MONEY_MULTI = 100;

    FieldType getType();

    /**
     * 组装请求参数
     * @param params 参数集合
     * @param context 请求上线问
     * @param key 参数key
     * @param defaultValue 默认值
     * @return
     */
    LinkedHashMap<String, Object> assemble(LinkedHashMap<String, Object> params, RequestContext context, String key, String defaultValue);

    /**
     * 转换同步返回参数
     * @param result 解析结果
     * @param value 值
     * @param assertValue 期望值
     */
    void convert(ParseResult result, Object value, String assertValue);

    /**
     * 回调解析参数
     * @param context 解析结果
     * @param resultParams 返回参数 用于验签
     * @param assertValue 期望值
     * @param key 参数key
     * @param requestParams 回调参数map
     */
    void callback(CallbackContext context, ParseResult result, LinkedHashMap<String, Object> resultParams, String assertValue, String key, Map<String, Object> requestParams);
}
