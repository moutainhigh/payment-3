package com.f.transport.field;

import com.f.enums.FieldType;
import com.f.transport.RequestContext;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @author : caibi
 * @date : 2019-01-15 21:25
 */
@Component
public class PayTypeField extends AbstractField {
    @Override
    public FieldType getType() {
        return FieldType.PAY_TYPE;
    }

    @Override
    public LinkedHashMap<String, Object> assemble(LinkedHashMap<String, Object> params, RequestContext context, String key, String defaultValue) {
        String payType = context.getData().get(FieldType.PAY_TYPE).toString();
        Object value = context.getPayTypeMap().get(payType);
        params.put(key, value);
        return params;
    }
}
