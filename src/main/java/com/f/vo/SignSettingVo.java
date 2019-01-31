package com.f.vo;

import com.f.enums.SignType;
import lombok.Data;

import java.util.Map;

/**
 * @author : caibi
 * @date : 2019-01-21 11:23
 */
@Data
public class SignSettingVo {
    private int type;
    private String foreignKey;//修改了数据库
    private SignType signType;
    private boolean containsKey;
    private String keyValueSplit;
    private String valueSplit;
    private String keyPrefix;
    private boolean keyFirst;
    private boolean upperCase;
    private Map<String, String> additionSetting;
}
