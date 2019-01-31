package com.f.vo;

import com.f.enums.FieldType;
import jdk.nashorn.internal.codegen.CompilerConstants;
import lombok.Data;

/**
 * @author : caibi
 * @date : 2019-01-16 11:08
 */
@Data
public class CallbackParamVo implements Comparable<CallbackParamVo> {
    private String fieldKey;
    private FieldType fieldType;
    private int fieldOrder;
    private String assertValue;

    @Override
    public int compareTo(CallbackParamVo o) {
        return fieldOrder - o.fieldOrder;
    }
}
