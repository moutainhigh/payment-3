package com.f.transport.field;

import com.f.enums.FieldType;
import com.f.transport.ParseResult;
import com.f.transport.RequestContext;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @author : caibi
 * @date : 2019-01-15 20:34
 */
@Component
public class YuanField extends AbstractField {
    @Override
    public FieldType getType() {
        return FieldType.YUAN;
    }

    @Override
    public LinkedHashMap<String, Object> assemble(LinkedHashMap<String, Object> params, RequestContext context, String key, String defaultValue) {
        params.put(key, context.getMoney());
        return params;
    }

    @Override
    public void convert(ParseResult result, Object value, String assertValue) {
        int fen = Math.round(Float.valueOf(value.toString()) * MONEY_MULTI);
        result.setFen(fen);
    }
}
