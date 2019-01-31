package com.f.transport.field;

import com.f.enums.FieldType;
import com.f.transport.RequestContext;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @author : caibi
 * @date : 2019-01-17 11:14
 */
@Component
public class TimeField extends AbstractField {
    public static final String SEC = "SEC";

    @Override
    public FieldType getType() {
        return FieldType.TIME;
    }

    @Override
    public LinkedHashMap<String, Object> assemble(LinkedHashMap<String, Object> params, RequestContext context, String key, String defaultValue) {
        long time = System.currentTimeMillis();
        if(SEC.equalsIgnoreCase(defaultValue)){
            time /= DateUtils.MILLIS_PER_SECOND;
        }
        params.put(key, time);
        return params;
    }
}
