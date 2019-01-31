package com.f.vo;

import com.f.enums.FieldType;
import lombok.Data;

/**
 * @author : caibi
 * @date : 2019-01-15 17:05
 */
@Data
public class RequestParamVo implements Comparable<RequestParamVo>{
    private String fieldKey;
    private FieldType fieldType;
    private String defaultValue;
    private int fieldOrder;

    @Override
    public int compareTo(RequestParamVo o) {
        return fieldOrder - o.fieldOrder;
    }

    public RequestParamVo clone(){
        RequestParamVo vo = new RequestParamVo();
        vo.setFieldKey(getFieldKey());
        vo.setFieldType(getFieldType());
        vo.setDefaultValue(getDefaultValue());
        vo.setFieldOrder(getFieldOrder());
        return vo;
    }
}
