package com.f.mvc.entity;

import com.f.enums.FieldType;
import com.f.vo.RequestParamVo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tbl_request_param")
public class RequestParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 请求id
     */
    @Column(name = "request_id")
    private Long requestId;

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
     * 默认值
     */
    @Column(name = "default_value")
    private String defaultValue;

    /**
     * 参数顺序
     */
    @Column(name = "field_order")
    private Integer fieldOrder;

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
     * 获取请求id
     *
     * @return request_id - 请求id
     */
    public Long getRequestId() {
        return requestId;
    }

    /**
     * 设置请求id
     *
     * @param requestId 请求id
     */
    public void setRequestId(Long requestId) {
        this.requestId = requestId;
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
     * 获取默认值
     *
     * @return default_value - 默认值
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * 设置默认值
     *
     * @param defaultValue 默认值
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
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

    public RequestParamVo transToVO(){
        RequestParamVo requestParamVo = new RequestParamVo();
        requestParamVo.setFieldKey(getFieldKey());
        requestParamVo.setFieldType(FieldType.valueOf(getFieldType()));
        requestParamVo.setDefaultValue(getDefaultValue());
        requestParamVo.setFieldOrder(getFieldOrder());
        return requestParamVo;
    }
}