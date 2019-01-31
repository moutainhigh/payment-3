package com.f.vo;

import com.f.enums.FieldType;
import lombok.Data;

/**
 * @author : caibi
 * @date : 2019-01-16 11:08
 */
@Data
public class ResponseParamVo {
    private String parseScript;
    private FieldType fieldType;
    private String assertValue;

    public ResponseParamVo clone(){
        ResponseParamVo vo = new ResponseParamVo();
        vo.setParseScript(getParseScript());
        vo.setFieldType(getFieldType());
        vo.setAssertValue(getAssertValue());
        return vo;
    }
}
