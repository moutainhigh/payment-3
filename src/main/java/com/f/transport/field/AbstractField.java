package com.f.transport.field;

import com.f.transport.CallbackContext;
import com.f.transport.ParseResult;
import com.f.transport.RequestContext;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : caibi
 * @date : 2019-01-17 12:05
 */
public abstract class AbstractField implements FieldProcessor{

    @Override
    public LinkedHashMap<String, Object> assemble(LinkedHashMap<String, Object> params, RequestContext context, String key, String defaultValue) {
        Object value = context.getData().get(getType());
        params.put(key, value);
        return params;
    }

    @Override
    public void convert(ParseResult result, Object value, String assertValue) {
        result.getDatas().put(getType(), value);
    }

    @Override
    public void callback(CallbackContext context, ParseResult result, LinkedHashMap<String, Object> resultParams, String assertValue, String key, Map<String, Object> requestParams) {
        Object value = requestParams.get(key);
        resultParams.put(key, value);
        convert(result, value, assertValue);
    }
}
