package com.f.transport;

import com.f.enums.FieldType;
import com.f.enums.RequestMethod;
import com.f.enums.SignType;
import com.f.vo.SignSettingVo;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author : caibi
 * @date : 2019-01-15 16:10
 */
public interface RequestContext extends Context {

    RequestMethod getRequestMethod();
    String getUrl();
    float getMoney();
    String getKey();
    Map<FieldType, Object> getData();
    Map<String, Object> getPayTypeMap();
    /**附加参数*/
    Map<String, String> getAdditionMap();
    SignSettingVo getSignSetting();
}
