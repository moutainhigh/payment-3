package com.f.mvc.service.withdraw;

/**
 * @author rebysfu@gmail.com
 * @description：支付请求抽象
 * @create 2019-01-31 下午12:51
 **/
public abstract class AbstractPaymentService {
    //1、获取渠道参数配置
    protected void getChannleConfig(String channelId, String merhcantId) {

    }

    //2、参数校验
    protected boolean verifyData() {
        return false;
    }

    //3、获取渠道加密方式

    protected void getSignCipher(String channelId) {}


//1、

}
