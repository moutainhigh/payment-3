package com.f.transport.sign;

import com.f.transport.RequestContext;
import com.f.vo.SignSettingVo;
import lombok.extern.log4j.Log4j2;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : caibi
 * @date : 2019-01-17 16:45
 */
@Log4j2
public abstract class AbstractSign implements SignProcessor {

    public String calcSourceStr(SignSettingVo setting, String key, LinkedHashMap<String, Object> params){
        StringBuilder builder = new StringBuilder();
        boolean containsKey = setting.isContainsKey();
        String keyValueSplit = setting.getKeyValueSplit();
        String valueSplit = setting.getValueSplit();

        Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Object> entry = it.next();
            if(containsKey){
                builder.append(entry.getKey());
                builder.append(keyValueSplit);
            }
            builder.append(entry.getValue());
            if(it.hasNext()){
                builder.append(valueSplit);
            }
        }

        String keyPrefix = setting.getKeyPrefix();
        keyPrefix += key;
        if(setting.isKeyFirst()){
            keyPrefix += valueSplit;
            builder.insert(0, keyPrefix);
        }else {
            builder.append(keyPrefix);
        }
        String srcStr = builder.toString();
        log.info("source string [{}]", srcStr);
        return srcStr;
    }
}
