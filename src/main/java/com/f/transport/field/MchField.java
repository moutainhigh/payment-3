package com.f.transport.field;

import com.f.enums.FieldType;
import org.springframework.stereotype.Component;

/**
 * @author : caibi
 * @date : 2019-01-15 20:31
 */
@Component
public class MchField extends AbstractField {
    @Override
    public FieldType getType() {
        return FieldType.MCH_ID;
    }
}
