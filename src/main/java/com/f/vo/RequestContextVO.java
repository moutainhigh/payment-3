package com.f.vo;
/*
 *@ClassName RequestContextVO
 *@Author 建国
 *@Date 1/21/19 6:25 PM
 */

import com.f.enums.FieldType;
import com.f.enums.RequestMethod;
import com.f.transport.RequestContext;
import lombok.Data;
import java.util.Map;

@Data
public class RequestContextVO implements RequestContext {

    RequestMethod requestMethod;

    String url;

    int money;

    String key;

    Map<FieldType, Object> data;

    Map<String, Object> payTypeMap;

    Map<String, String> additionMap;

    SignSettingVo signSettingVo;

    @Override
    public RequestMethod getRequestMethod() {
        return this.requestMethod;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public float getMoney() {
        return this.money;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Map<FieldType, Object> getData() {
        return this.data;
    }

    @Override
    public Map<String, Object> getPayTypeMap() {
        return this.payTypeMap;
    }

    @Override
    public Map<String, String> getAdditionMap() {
        return this.additionMap;
    }

    @Override
    public SignSettingVo getSignSetting() {
        return this.signSettingVo;
    }
}
