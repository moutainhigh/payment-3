package com.f.mvc.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tbl_callback_param")
public class PaymentCallbackParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 回调id
     */
    @Column(name = "callback_id")
    private Long callbackId;

    /**
     * 参数key
     */
    @Column(name = "field_key")
    private String fieldKey;

    /**
     * 参数类型 枚举 mchid orderid等
     */
    @Column(name = "field_type")
    private String fieldType;

    /**
     * 参数顺序
     */
    @Column(name = "field_order")
    private Integer fieldOrder;

    /**
     * 期望值
     */
    @Column(name = "assert_value")
    private String assertValue;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 修改人
     */
    @Column(name = "modify_user_id")
    private Long modifyUserId;

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
     * 获取回调id
     *
     * @return callback_id - 回调id
     */
    public Long getCallbackId() {
        return callbackId;
    }

    /**
     * 设置回调id
     *
     * @param callbackId 回调id
     */
    public void setCallbackId(Long callbackId) {
        this.callbackId = callbackId;
    }

    /**
     * 获取参数key
     *
     * @return field_key - 参数key
     */
    public String getFieldKey() {
        return fieldKey;
    }

    /**
     * 设置参数key
     *
     * @param fieldKey 参数key
     */
    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    /**
     * 获取参数类型 枚举 mchid orderid等
     *
     * @return field_type - 参数类型 枚举 mchid orderid等
     */
    public String getFieldType() {
        return fieldType;
    }

    /**
     * 设置参数类型 枚举 mchid orderid等
     *
     * @param fieldType 参数类型 枚举 mchid orderid等
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * 获取参数顺序
     *
     * @return field_order - 参数顺序
     */
    public Integer getFieldOrder() {
        return fieldOrder;
    }

    /**
     * 设置参数顺序
     *
     * @param fieldOrder 参数顺序
     */
    public void setFieldOrder(Integer fieldOrder) {
        this.fieldOrder = fieldOrder;
    }

    /**
     * 获取期望值
     *
     * @return assert_value - 期望值
     */
    public String getAssertValue() {
        return assertValue;
    }

    /**
     * 设置期望值
     *
     * @param assertValue 期望值
     */
    public void setAssertValue(String assertValue) {
        this.assertValue = assertValue;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取修改人
     *
     * @return modify_user_id - 修改人
     */
    public Long getModifyUserId() {
        return modifyUserId;
    }

    /**
     * 设置修改人
     *
     * @param modifyUserId 修改人
     */
    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
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