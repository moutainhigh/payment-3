package com.f.transport.field;

import com.f.enums.FieldType;
import com.f.transport.ParseResult;
import com.f.transport.RequestContext;
import com.f.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @author : caibi
 * @date : 2019-01-15 21:32
 */
@Component
public class CodeField extends AbstractField {
    @Override
    public FieldType getType() {
        return FieldType.CODE;
    }

    @Override
    public void convert(ParseResult result, Object value, String assertValue) {
        String strValue = value.toString();
        if(StringUtils.isNotEmpty(assertValue) && strValue.equals(assertValue)){
            result.setSuccess(true);
        }
        result.setCode(strValue);
    }
}
