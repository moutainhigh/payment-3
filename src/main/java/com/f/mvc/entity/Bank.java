package com.f.mvc.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tbl_bank")
public class Bank {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
     * 银行卡号
     */
    @Column(name = "card_no")
    private Long cardNo;

    /**
     * 开户人
     */
    private String name;

    /**
     * 开户行
     */
    @Column(name = "bank_branch")
    private String bankBranch;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区/县
     */
    private String county;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取开户人
     *
     * @return name - 开户人
     */
    public String getName() {
        return name;
    }

    /**
     * 设置开户人
     *
     * @param name 开户人
     */
    public void setName(String name) {
        this.name = name;
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
     * 获取省
     *
     * @return province - 省
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省
     *
     * @param province 省
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取市
     *
     * @return city - 市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置市
     *
     * @param city 市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取区/县
     *
     * @return county - 区/县
     */
    public String getCounty() {
        return county;
    }

    /**
     * 设置区/县
     *
     * @param county 区/县
     */
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}