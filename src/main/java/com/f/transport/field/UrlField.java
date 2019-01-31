package com.f.transport.field;

import com.f.enums.FieldType;
import com.f.transport.ParseResult;
import org.springframework.stereotype.Component;

/**
 * @author : caibi
 * @date : 2019-01-17 11:53
 */
@Component
public class UrlField extends AbstractField {
    @Override
    public FieldType getType() {
        return FieldType.TARGET_URL;
    }

    @Override
    public void convert(ParseResult result, Object value, String assertValue) {
        result.setTargetUrl(value.toString());
    }
}
