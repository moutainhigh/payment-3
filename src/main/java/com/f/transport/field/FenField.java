package com.f.transport.field;

import com.f.enums.FieldType;
import com.f.transport.ParseResult;
import com.f.transport.RequestContext;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @author : caibi
 * @date : 2019-01-15 20:38
 */
@Component
public class FenField extends AbstractField {
    @Override
    public FieldType getType() {
        return FieldType.FEN;
    }

    @Override
    public LinkedHashMap<String, Object> assemble(LinkedHashMap<String, Object> params, RequestContext context, String key, String defaultValue) {
        int fen = Math.round(context.getMoney() * MONEY_MULTI);
        params.put(key, fen);
        return params;
    }

    @Override
    public void convert(ParseResult result, Object value, String assertValue) {
        int fen = Integer.parseInt(value.toString());
        result.setFen(fen);
    }
}
