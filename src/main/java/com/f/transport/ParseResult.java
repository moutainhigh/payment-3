package com.f.transport;

import com.f.enums.FieldType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : caibi
 * @date : 2019-01-15 15:38
 */
@Data
public class ParseResult {
    /**是否成功*/
    private boolean success;
    private String code;
    private String msg;
    private String sign;
    private int fen;
    private String content;
    private String targetUrl;
    private Map<FieldType, Object> datas = new HashMap<>();
}
