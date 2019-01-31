package com.f.transport.field;

import com.f.enums.FieldType;
import com.f.transport.ParseResult;
import com.f.utils.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author : caibi
 * @date : 2019-01-17 11:50
 */
@Component
public class ContentField extends AbstractField {

    @Override
    public FieldType getType() {
        return FieldType.TARGET_CONTENT;
    }

    @Override
    public void convert(ParseResult result, Object value, String assertValue) {
        String targetValue = value.toString();
        if(StringUtils.isNotEmpty(assertValue) && !targetValue.startsWith(assertValue)){
            throw new RuntimeException("返回值不正确");
        }
        result.setContent(targetValue);
    }
}
