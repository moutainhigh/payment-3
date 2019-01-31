package com.f.mvc.service.channle;

import com.f.enums.FieldType;
import lombok.Data;

/**
 * @author rebysfu@gmail.com
 * @description：
 * @create 2019-01-28 上午11:02
 **/
@Data
public class ParamsConfig {
    private String fieldKey;
    private FieldType fieldType;
    private String defaultValue;
    private int fieldOrder;
}
