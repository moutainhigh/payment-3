package com.f.mvc.entity;

import com.f.enums.FieldType;
import com.f.vo.RequestParamVo;
import com.f.vo.ResponseParamVo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tbl_response_param")
public class ResponseParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 请求id
     */
    @Column(name = "request_id")
    private Long requestId;

    /**
     * 参数类型 枚举 mchid orderid等
     */
    @Column(name = "field_type")
    private String fieldType;

    /**
     * 解析脚本
     */
    @Column(name = "parse_script")
    private String parseScript;

    /**
     * 成功返回值
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
     * 获取解析脚本
     *
     * @return parse_script - 解析脚本
     */
    public String getParseScript() {
        return parseScript;
    }

    /**
     * 设置解析脚本
     *
     * @param parseScript 解析脚本
     */
    public void setParseScript(String parseScript) {
        this.parseScript = parseScript;
    }

    /**
     * 获取成功返回值
     *
     * @return assert_value - 成功返回值
     */
    public String getAssertValue() {
        return assertValue;
    }

    /**
     * 设置成功返回值
     *
     * @param assertValue 成功返回值
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

    public ResponseParamVo transToVO(){
        ResponseParamVo responseParamVo = new ResponseParamVo();
        responseParamVo.setParseScript(getParseScript());
        responseParamVo.setFieldType(FieldType.valueOf(getFieldType()));
        responseParamVo.setAssertValue(getAssertValue());
        return responseParamVo;
    }
}