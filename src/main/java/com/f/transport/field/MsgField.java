package com.f.transport.field;

import com.f.enums.FieldType;
import com.f.transport.ParseResult;
import com.f.transport.RequestContext;
import com.f.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @author : caibi
 * @date : 2019-01-15 21:33
 */
@Component
public class MsgField extends AbstractField {
    @Override
    public FieldType getType() {
        return FieldType.MSG;
    }

    @Override
    public LinkedHashMap<String, Object> assemble(LinkedHashMap<String, Object> params, RequestContext context, String key, String defaultValue) {
        params.put(key, defaultValue);
        return params;
    }

    @Override
    public void convert(ParseResult result, Object value, String assertValue) {
        String strValue = value.toString();
        if(StringUtils.isNotEmpty(assertValue) && strValue.equals(assertValue)){
            result.setSuccess(true);
        }
        result.setMsg(strValue);
    }
}
