package com.f.transport.field;

import com.f.enums.FieldType;
import org.springframework.stereotype.Component;

/**
 * @author : caibi
 * @date : 2019-01-15 21:28
 */
@Component
public class ReturnUrlField extends AbstractField {
    @Override
    public FieldType getType() {
        return FieldType.RETURN_URL;
    }
}
