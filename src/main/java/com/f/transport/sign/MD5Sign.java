package com.f.transport.sign;

import com.f.enums.SignType;
import com.f.transport.RequestContext;
import com.f.vo.SignSettingVo;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : caibi
 * @date : 2019-01-16 16:43
 */
@Component
public class MD5Sign extends AbstractSign {

    @Override
    public SignType getType() {
        return SignType.MD5;
    }

    @Override
    public String sign(SignSettingVo setting, String key, LinkedHashMap<String, Object> params) {
        String srcStr = calcSourceStr(setting, key, params);
        String sign = Hashing.md5().hashString(srcStr, Charsets.UTF_8).toString();
        if(setting.isUpperCase()){
            sign = sign.toUpperCase();
        }
        return sign;
    }
}
