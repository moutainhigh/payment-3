package com.f.transport.field;

import com.f.enums.FieldType;
import com.f.transport.RequestContext;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @author : caibi
 * @date : 2019-01-17 11:11
 */
@Component
public class DateField extends AbstractField {
    @Override
    public FieldType getType() {
        return FieldType.DATE;
    }

    @Override
    public LinkedHashMap<String, Object> assemble(LinkedHashMap<String, Object> params, RequestContext context, String key, String defaultValue) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(defaultValue);
        String str = format.format(date);
        params.put(key, str);
        return params;
    }
}
