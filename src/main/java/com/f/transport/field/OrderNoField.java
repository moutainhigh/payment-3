package com.f.transport.field;

import com.f.enums.FieldType;
import org.springframework.stereotype.Component;

/**
 * @author : caibi
 * @date : 2019-01-15 20:45
 */
@Component
public class OrderNoField extends AbstractField {

    @Override
    public FieldType getType() {
        return FieldType.ORDER_NO;
    }
}
