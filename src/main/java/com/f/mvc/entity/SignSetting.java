package com.f.mvc.entity;

import javax.persistence.*;

@Table(name = "tbl_sign_setting")
public class SignSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 配置类型 0渠道配置 1单个请求配置 2回调配置
     */
    private Integer type;

    /**
     * 外键 渠道id、请求id
     */
    @Column(name = "foreign_key")
    private String foreignKey;

    /**
     * 签名类型 枚举
     */
    @Column(name = "sign_type")
    private String signType;

    /**
     * 原串是否包含key
     */
    @Column(name = "contains_key")
    private Boolean containsKey;

    /**
     * key与value间隔符
     */
    @Column(name = "key_value_split")
    private String keyValueSplit;

    /**
     * value之间间隔符
     */
    @Column(name = "value_split")
    private String valueSplit;

    /**
     * 商户key前缀
     */
    @Column(name = "key_prefix")
    private String keyPrefix;

    /**
     * key是否在前面
     */
    @Column(name = "key_first")
    private Boolean keyFirst;

    /**
     * 转换大写
     */
    @Column(name = "upper_case")
    private Boolean upperCase;

    /**
     * 额外配置 json
     */
    @Column(name = "addition_setting")
    private String additionSetting;

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
     * 获取配置类型 0渠道配置 1单个请求配置 2回调配置
     *
     * @return type - 配置类型 0渠道配置 1单个请求配置 2回调配置
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置配置类型 0渠道配置 1单个请求配置 2回调配置
     *
     * @param type 配置类型 0渠道配置 1单个请求配置 2回调配置
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取外键 渠道id、请求id
     *
     * @return foreign_key - 外键 渠道id、请求id
     */
    public String getForeignKey() {
        return foreignKey;
    }

    /**
     * 设置外键 渠道id、请求id
     *
     * @param foreignKey 外键 渠道id、请求id
     */
    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    /**
     * 获取签名类型 枚举
     *
     * @return sign_type - 签名类型 枚举
     */
    public String getSignType() {
        return signType;
    }

    /**
     * 设置签名类型 枚举
     *
     * @param signType 签名类型 枚举
     */
    public void setSignType(String signType) {
        this.signType = signType;
    }

    /**
     * 获取原串是否包含key
     *
     * @return contains_key - 原串是否包含key
     */
    public Boolean getContainsKey() {
        return containsKey;
    }

    /**
     * 设置原串是否包含key
     *
     * @param containsKey 原串是否包含key
     */
    public void setContainsKey(Boolean containsKey) {
        this.containsKey = containsKey;
    }

    /**
     * 获取key与value间隔符
     *
     * @return key_value_split - key与value间隔符
     */
    public String getKeyValueSplit() {
        return keyValueSplit;
    }

    /**
     * 设置key与value间隔符
     *
     * @param keyValueSplit key与value间隔符
     */
    public void setKeyValueSplit(String keyValueSplit) {
        this.keyValueSplit = keyValueSplit;
    }

    /**
     * 获取value之间间隔符
     *
     * @return value_split - value之间间隔符
     */
    public String getValueSplit() {
        return valueSplit;
    }

    /**
     * 设置value之间间隔符
     *
     * @param valueSplit value之间间隔符
     */
    public void setValueSplit(String valueSplit) {
        this.valueSplit = valueSplit;
    }

    /**
     * 获取商户key前缀
     *
     * @return key_prefix - 商户key前缀
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * 设置商户key前缀
     *
     * @param keyPrefix 商户key前缀
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    /**
     * 获取key是否在前面
     *
     * @return key_first - key是否在前面
     */
    public Boolean getKeyFirst() {
        return keyFirst;
    }

    /**
     * 设置key是否在前面
     *
     * @param keyFirst key是否在前面
     */
    public void setKeyFirst(Boolean keyFirst) {
        this.keyFirst = keyFirst;
    }

    /**
     * 获取转换大写
     *
     * @return upper_case - 转换大写
     */
    public Boolean getUpperCase() {
        return upperCase;
    }

    /**
     * 设置转换大写
     *
     * @param upperCase 转换大写
     */
    public void setUpperCase(Boolean upperCase) {
        this.upperCase = upperCase;
    }

    /**
     * 获取额外配置 json
     *
     * @return addition_setting - 额外配置 json
     */
    public String getAdditionSetting() {
        return additionSetting;
    }

    /**
     * 设置额外配置 json
     *
     * @param additionSetting 额外配置 json
     */
    public void setAdditionSetting(String additionSetting) {
        this.additionSetting = additionSetting;
    }
}