package com.f.transport.field;

import com.f.enums.FieldType;
import org.springframework.stereotype.Component;

/**
 * @author : caibi
 * @date : 2019-01-17 11:20
 */
@Component
public class IpField extends AbstractField {

    @Override
    public FieldType getType() {
        return FieldType.IP;
    }
}
