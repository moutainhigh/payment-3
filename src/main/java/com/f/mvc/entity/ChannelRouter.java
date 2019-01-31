package com.f.mvc.entity;

import javax.persistence.*;

@Table(name = "tbl_channel_router")
public class ChannelRouter {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 应用id
     */
    private String appid;

    /**
     * 应用名称
     */
    @Column(name = "app_name")
    private String appName;

    /**
     * 商户公钥
     */
    private String appkey;

    /**
     * 签名方式  1 参加加密字段按照ascci 从大到小加密 2 按照指定的字符串属性加密 参数的order属性
     */
    @Column(name = "sign_type")
    private Integer signType;

    /**
     * 渠道参数配置id
     */
    @Column(name = "channel_setting_id")
    private Long channelSettingId;

    /**
     * 状态 0 启用 1 停用
     */
    private Integer status;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取应用id
     *
     * @return appid - 应用id
     */
    public String getAppid() {
        return appid;
    }

    /**
     * 设置应用id
     *
     * @param appid 应用id
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     * 获取应用名称
     *
     * @return app_name - 应用名称
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置应用名称
     *
     * @param appName 应用名称
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * 获取商户公钥
     *
     * @return appkey - 商户公钥
     */
    public String getAppkey() {
        return appkey;
    }

    /**
     * 设置商户公钥
     *
     * @param appkey 商户公钥
     */
    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    /**
     * 获取签名方式  1 参加加密字段按照ascci 从大到小加密 2 按照指定的字符串属性加密 参数的order属性
     *
     * @return sign_type - 签名方式  1 参加加密字段按照ascci 从大到小加密 2 按照指定的字符串属性加密 参数的order属性
     */
    public Integer getSignType() {
        return signType;
    }

    /**
     * 设置签名方式  1 参加加密字段按照ascci 从大到小加密 2 按照指定的字符串属性加密 参数的order属性
     *
     * @param signType 签名方式  1 参加加密字段按照ascci 从大到小加密 2 按照指定的字符串属性加密 参数的order属性
     */
    public void setSignType(Integer signType) {
        this.signType = signType;
    }

    /**
     * 获取渠道参数配置id
     *
     * @return channel_setting_id - 渠道参数配置id
     */
    public Long getChannelSettingId() {
        return channelSettingId;
    }

    /**
     * 设置渠道参数配置id
     *
     * @param channelSettingId 渠道参数配置id
     */
    public void setChannelSettingId(Long channelSettingId) {
        this.channelSettingId = channelSettingId;
    }

    /**
     * 获取状态 0 启用 1 停用
     *
     * @return status - 状态 0 启用 1 停用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0 启用 1 停用
     *
     * @param status 状态 0 启用 1 停用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}