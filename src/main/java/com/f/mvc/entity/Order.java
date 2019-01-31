package com.f.mvc.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tbl_order")
public class Order {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 渠道ID（渠道编码）
     */
    @Column(name = "channel_id")
    private String channelId;

    /**
     * 渠道名称(第三方或者第四方渠道的名称，如：哇哈哈支付)
     */
    @Column(name = "channel_name")
    private String channelName;

    /**
     * 商户订单号（系统生成）
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 第三方系统订单号（流水号）
     */
    @Column(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 商户账号
     */
    @Column(name = "merht_account")
    private String merhtAccount;

    /**
     * 商户ID(第三方渠道提供的商户号)
     */
    @Column(name = "merchant_id")
    private String merchantId;

    /**
     * 商户名称（可以是公司的名字skline）
     */
    @Column(name = "merchant_name")
    private String merchantName;

    /**
     * 银行卡号
     */
    @Column(name = "card_no")
    private Long cardNo;

    /**
     * 银行编码
     */
    @Column(name = "bank_code")
    private String bankCode;

    /**
     * 银行名称
     */
    @Column(name = "bank_name")
    private String bankName;

    /**
     * 开户人
     */
    @Column(name = "account_name")
    private String accountName;

    /**
     * 开户行
     */
    @Column(name = "bank_branch")
    private String bankBranch;

    /**
     * 交易金额(单位:分)
     */
    private Integer amount;

    /**
     * 订单创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 订单完成时间
     */
    @Column(name = "complete_time")
    private Date completeTime;

    /**
     * 订单状态(状态 1 提现中 2 成功 3 失败 )
     */
    private Integer status;

    /**
     * 说明
     */
    private String note;

    /**
     * 订单请求原始内容（记录请求的url）
     */
    @Column(name = "req_json")
    private String reqJson;

    /**
     * 订单请求响应原始内容(记录同步返回的url,便于查问题)
     */
    @Column(name = "res_json")
    private String resJson;

    /**
     * 渠道返回结果
     */
    @Column(name = "channel_result")
    private String channelResult;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取渠道ID（渠道编码）
     *
     * @return channel_id - 渠道ID（渠道编码）
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * 设置渠道ID（渠道编码）
     *
     * @param channelId 渠道ID（渠道编码）
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * 获取渠道名称(第三方或者第四方渠道的名称，如：哇哈哈支付)
     *
     * @return channel_name - 渠道名称(第三方或者第四方渠道的名称，如：哇哈哈支付)
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * 设置渠道名称(第三方或者第四方渠道的名称，如：哇哈哈支付)
     *
     * @param channelName 渠道名称(第三方或者第四方渠道的名称，如：哇哈哈支付)
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * 获取商户订单号（系统生成）
     *
     * @return order_no - 商户订单号（系统生成）
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置商户订单号（系统生成）
     *
     * @param orderNo 商户订单号（系统生成）
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取第三方系统订单号（流水号）
     *
     * @return out_trade_no - 第三方系统订单号（流水号）
     */
    public String getOutTradeNo() {
        return outTradeNo;
    }

    /**
     * 设置第三方系统订单号（流水号）
     *
     * @param outTradeNo 第三方系统订单号（流水号）
     */
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    /**
     * 获取商户账号
     *
     * @return merht_account - 商户账号
     */
    public String getMerhtAccount() {
        return merhtAccount;
    }

    /**
     * 设置商户账号
     *
     * @param merhtAccount 商户账号
     */
    public void setMerhtAccount(String merhtAccount) {
        this.merhtAccount = merhtAccount;
    }

    /**
     * 获取商户ID(第三方渠道提供的商户号)
     *
     * @return merchant_id - 商户ID(第三方渠道提供的商户号)
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     * 设置商户ID(第三方渠道提供的商户号)
     *
     * @param merchantId 商户ID(第三方渠道提供的商户号)
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
     * 获取银行卡号
     *
     * @return card_no - 银行卡号
     */
    public Long getCardNo() {
        return cardNo;
    }

    /**
     * 设置银行卡号
     *
     * @param cardNo 银行卡号
     */
    public void setCardNo(Long cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * 获取银行编码
     *
     * @return bank_code - 银行编码
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * 设置银行编码
     *
     * @param bankCode 银行编码
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * 获取银行名称
     *
     * @return bank_name - 银行名称
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 设置银行名称
     *
     * @param bankName 银行名称
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 获取开户人
     *
     * @return account_name - 开户人
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置开户人
     *
     * @param accountName 开户人
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取开户行
     *
     * @return bank_branch - 开户行
     */
    public String getBankBranch() {
        return bankBranch;
    }

    /**
     * 设置开户行
     *
     * @param bankBranch 开户行
     */
    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    /**
     * 获取交易金额(单位:分)
     *
     * @return amount - 交易金额(单位:分)
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * 设置交易金额(单位:分)
     *
     * @param amount 交易金额(单位:分)
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * 获取订单创建时间
     *
     * @return create_time - 订单创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置订单创建时间
     *
     * @param createTime 订单创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取订单完成时间
     *
     * @return complete_time - 订单完成时间
     */
    public Date getCompleteTime() {
        return completeTime;
    }

    /**
     * 设置订单完成时间
     *
     * @param completeTime 订单完成时间
     */
    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    /**
     * 获取订单状态(状态 1 提现中 2 成功 3 失败 )
     *
     * @return status - 订单状态(状态 1 提现中 2 成功 3 失败 )
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置订单状态(状态 1 提现中 2 成功 3 失败 )
     *
     * @param status 订单状态(状态 1 提现中 2 成功 3 失败 )
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取说明
     *
     * @return note - 说明
     */
    public String getNote() {
        return note;
    }

    /**
     * 设置说明
     *
     * @param note 说明
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 获取订单请求原始内容（记录请求的url）
     *
     * @return req_json - 订单请求原始内容（记录请求的url）
     */
    public String getReqJson() {
        return reqJson;
    }

    /**
     * 设置订单请求原始内容（记录请求的url）
     *
     * @param reqJson 订单请求原始内容（记录请求的url）
     */
    public void setReqJson(String reqJson) {
        this.reqJson = reqJson;
    }

    /**
     * 获取订单请求响应原始内容(记录同步返回的url,便于查问题)
     *
     * @return res_json - 订单请求响应原始内容(记录同步返回的url,便于查问题)
     */
    public String getResJson() {
        return resJson;
    }

    /**
     * 设置订单请求响应原始内容(记录同步返回的url,便于查问题)
     *
     * @param resJson 订单请求响应原始内容(记录同步返回的url,便于查问题)
     */
    public void setResJson(String resJson) {
        this.resJson = resJson;
    }

    /**
     * 获取渠道返回结果
     *
     * @return channel_result - 渠道返回结果
     */
    public String getChannelResult() {
        return channelResult;
    }

    /**
     * 设置渠道返回结果
     *
     * @param channelResult 渠道返回结果
     */
    public void setChannelResult(String channelResult) {
        this.channelResult = channelResult;
    }
}