package com.f.mvc.service.notify;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author rebysfu@gmail.com
 * @description：回调服务策略 。提现 和充值回调的一个通用接口
 * @create 2019-01-28 下午12:03
 **/
public interface NotifyService {
    /*
     * @Author rebysfu@gmail.com
     * @Description 加载支付回调信息
     * @Date 下午12:15 2019/1/29
     * @Param [channleId, merchantId, request]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> loadPayInfo(String channleId, String merchantId, HttpServletRequest request) throws IOException;

    /*
     * @Author rebysfu@gmail.com
     * @Description  获得返回给支付平台代表成功的
     * @Date 下午12:15 2019/1/29
     * @Param [channleId]
     * @return java.lang.String
     **/
    String getSuccessfulResponseData(String channleId);

    /*
     * @Author rebysfu@gmail.com
     * @Description  获得返回给支付平台代表失败
     * @Date 下午12:14 2019/1/29
     * @Param [channleId]
     * @return java.lang.String
     **/
    String getFailedResponseData(String channleId);

    /*
     * @Author rebysfu@gmail.com
     * @Description 判断是否支付成功,更新订单未失败
     * @Date 下午12:14 2019/1/29
     * @Param [channleId, merchantId, data]
     * @return boolean
     **/
    boolean isWithDrawSuccess(String channleId, String merchantId, Map<String, Object> data);


    /*
     * @Author rebysfu@gmail.com
     * @Description  验证数据的合法性 签名是否合法，必填参数
     * @Date 下午12:13 2019/1/29
     * @Param [channleId, merchantId, postData]
     * @return boolean
     **/
    boolean verifyData(String channleId, String merchantId, Map<String, Object> postData);

    /*
     * @Author rebysfu@gmail.com
     * @Description:成功后需要更新订单状态
     * @Date 下午6:24 2019/1/28
     * @Param [orderNo, outTradeNo, payInterfaceType] 系统订单号，流水号 类型
     * @return void
     **/
    void withDrawSuccess(String channleId, String merchantId, Map<String, Object> params);

}
