package com.f.mvc.service.channle;

import java.util.Optional;

/**
 * @author rebysfu@gmail.com
 * @description：渠道参数配置管理器,用于管理商户的一些配置,如商户密钥、请求参数、加密配置、回调配置、解析配置等等
 * @create 2019-01-29 下午5:20
 **/
public interface ChannleParamsConfigManager {


    /*
     * @Author rebysfu@gmail.com
     * @Description:获取渠道配置信息
     * @Date 下午5:36 2019/1/29
     * @Param [channleId, key] 渠道编码  key
     * @return com.f.mvc.service.channle.ChannleParamsConfig
     **/
    ChannleParamsConfig getChannleParamsConfig(String channleId, MerchantConfigKey key  );

    <T extends ChannleParamsConfig> Optional<T> getChannelConfigById(String channelConfigId, Class<T> tClass);


}
