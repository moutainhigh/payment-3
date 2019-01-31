package com.f.transport;

import com.f.vo.SignSettingVo;

/**
 * @author : caibi
 * @date : 2019-01-21 15:41
 */
public interface CallbackContext extends Context {

    /**商户key*/
    String getKey();
    SignSettingVo getSignSetting();
}
