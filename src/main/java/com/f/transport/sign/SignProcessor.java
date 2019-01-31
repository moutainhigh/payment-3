package com.f.transport.sign;

import com.f.enums.SignType;
import com.f.mvc.entity.SignSetting;
import com.f.transport.RequestContext;
import com.f.vo.SignSettingVo;

import java.util.LinkedHashMap;

/**
 * @author : caibi
 * @date : 2019-01-16 16:22
 */
public interface SignProcessor {
    SignType getType();
    String sign(SignSettingVo setting, String key, LinkedHashMap<String, Object> params);
}
