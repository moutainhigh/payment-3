package com.f.mvc.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tbl_transactionInfo")
public class TransactionInfo {
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
    @Column(name = "channle_name")
    private String channleName;

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
     * 交易总额
     */
    @Column(name = "total_amt")
    private Integer totalAmt;

    /**
     * 昨日交易总额
     */
    @Column(name = "total_fee_t1")
    private Integer totalFeeT1;

    /**
     * 今日交易总额
     */
    @Column(name = "total_fee_t0")
    private Integer totalFeeT0;

    /**
     * 结算金额
     */
    private Integer fee;

    /**
     * 可结算金额
     */
    @Column(name = "tran_fee")
    private Integer tranFee;

    /**
     * 领取状态
     */
    private Integer status;

    /**
     * 领取时间
     */
    @Column(name = "receive_time")
    private Date receiveTime;

    /**
     * 领取人
     */
    @Column(name = "receive_user_id")
    private Long receiveUserId;

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
     * @return channle_name - 渠道名称
     */
    public String getChannleName() {
        return channleName;
    }

    /**
     * 设置渠道名称
     *
     * @param channleName 渠道名称
     */
    public void setChannleName(String channleName) {
        this.channleName = channleName;
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
     * 获取交易总额
     *
     * @return total_amt - 交易总额
     */
    public Integer getTotalAmt() {
        return totalAmt;
    }

    /**
     * 设置交易总额
     *
     * @param totalAmt 交易总额
     */
    public void setTotalAmt(Integer totalAmt) {
        this.totalAmt = totalAmt;
    }

    /**
     * 获取昨日交易总额
     *
     * @return total_fee_t1 - 昨日交易总额
     */
    public Integer getTotalFeeT1() {
        return totalFeeT1;
    }

    /**
     * 设置昨日交易总额
     *
     * @param totalFeeT1 昨日交易总额
     */
    public void setTotalFeeT1(Integer totalFeeT1) {
        this.totalFeeT1 = totalFeeT1;
    }

    /**
     * 获取今日交易总额
     *
     * @return total_fee_t0 - 今日交易总额
     */
    public Integer getTotalFeeT0() {
        return totalFeeT0;
    }

    /**
     * 设置今日交易总额
     *
     * @param totalFeeT0 今日交易总额
     */
    public void setTotalFeeT0(Integer totalFeeT0) {
        this.totalFeeT0 = totalFeeT0;
    }

    /**
     * 获取结算金额
     *
     * @return fee - 结算金额
     */
    public Integer getFee() {
        return fee;
    }

    /**
     * 设置结算金额
     *
     * @param fee 结算金额
     */
    public void setFee(Integer fee) {
        this.fee = fee;
    }

    /**
     * 获取可结算金额
     *
     * @return tran_fee - 可结算金额
     */
    public Integer getTranFee() {
        return tranFee;
    }

    /**
     * 设置可结算金额
     *
     * @param tranFee 可结算金额
     */
    public void setTranFee(Integer tranFee) {
        this.tranFee = tranFee;
    }

    /**
     * 获取领取状态
     *
     * @return status - 领取状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置领取状态
     *
     * @param status 领取状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取领取时间
     *
     * @return receive_time - 领取时间
     */
    public Date getReceiveTime() {
        return receiveTime;
    }

    /**
     * 设置领取时间
     *
     * @param receiveTime 领取时间
     */
    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * 获取领取人
     *
     * @return receive_user_id - 领取人
     */
    public Long getReceiveUserId() {
        return receiveUserId;
    }

    /**
     * 设置领取人
     *
     * @param receiveUserId 领取人
     */
    public void setReceiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }
}