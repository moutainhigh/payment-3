package com.f.mvc.entity;

import javax.persistence.*;

@Table(name = "tbl_channel_merchant")
public class ChannelMerchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 渠道编码
     */
    @Column(name = "channel_id")
    private String channelId;

    /**
     * 渠道名称
     */
    @Column(name = "channel_name")
    private String channelName;

    /**
     * 商户号
     */
    @Column(name = "merchant_id")
    private String merchantId;

    /**
     * 商户名称（可以是公司的名字skline）
     */
    @Column(name = "merchant_name")
    private String merchantName;

    /**
     * 收款账户号
     */
    @Column(name = "account_no")
    private String accountNo;

    /**
     * 收款账户名
     */
    @Column(name = "account_name")
    private String accountName;

    /**
     * 商户key
     */
    private String appkey;

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
     * 获取渠道编码
     *
     * @return channel_id - 渠道编码
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * 设置渠道编码
     *
     * @param channelId 渠道编码
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * 获取渠道名称
     *
     * @return channel_name - 渠道名称
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * 设置渠道名称
     *
     * @param channelName 渠道名称
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * 获取商户号
     *
     * @return merchant_id - 商户号
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     * 设置商户号
     *
     * @param merchantId 商户号
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * 获取商户名称（可以是公司的名字skline）
     *
     * @return merchant_name - 商户名称（可以是公司的名字skline）
     */
    public String getMerchantName() {
        return merchantName;
    }

    /**
     * 设置商户名称（可以是公司的名字skline）
     *
     * @param merchantName 商户名称（可以是公司的名字skline）
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    /**
     * 获取收款账户号
     *
     * @return account_no - 收款账户号
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * 设置收款账户号
     *
     * @param accountNo 收款账户号
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * 获取收款账户名
     *
     * @return account_name - 收款账户名
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置收款账户名
     *
     * @param accountName 收款账户名
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取商户key
     *
     * @return appkey - 商户key
     */
    public String getAppkey() {
        return appkey;
    }

    /**
     * 设置商户key
     *
     * @param appkey 商户key
     */
    public void setAppkey(String appkey) {
        this.appkey = appkey;
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