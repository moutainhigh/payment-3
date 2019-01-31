package com.f.mvc.service.channle;

import com.f.enums.PayInterfaceType;
import com.f.enums.RequestMethod;
import com.f.enums.ResponseParseType;
import com.f.mvc.entity.SignSetting;
import lombok.Data;

import java.util.List;

/**
 * @author rebysfu@gmail.com
 * @description：渠道参数配置
 * @create 2019-01-28 上午10:55
 **/
@Data
public class ChannleParamsConfig {
    /**
     * 渠道编码
     */
    private String channelId;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 商户号
     */
    private String merchantId;
    /**
     * 商户名称
     */
    private String merchantName;


    /*
     *接口类型
     * */
    private PayInterfaceType payInterfaceType;

    /**
     * 请求方法
     */
    private RequestMethod requestMethod;

    /**
     * url(网关地址)
     */
    private String url;

    /**
     * 返回值解析方式 枚举 正则 spel 脚本 html
     */
    private ResponseParseType parseType;

    /**
     * 参数
     */
    private List<ParamsConfig> paramsConfigs;

    /**
     * 签名配置
     */
    private SignSetting signSetting;
}
