package com.f.mvc.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tbl_callback")
public class PaymentCallback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 渠道编码
     */
    @Column(name = "channel_id")
    private String channelId;

    /**
     * 回调类型枚举 0 代付提现 1 下单
     */
    private Integer type;

    /**
     * 处理类型 0实现类 1通用配置
     */
    @Column(name = "process_type")
    private Integer processType;

    /**
     * 成功返回字符串
     */
    @Column(name = "success_response")
    private String successResponse;

    /**
     * 失败返回字符串
     */
    @Column(name = "error_response")
    private String errorResponse;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 创建人
     */
    @Column(name = "create_user_id")
    private Long createUserId;

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
     * 获取回调类型枚举 0 代付提现 1 下单
     *
     * @return type - 回调类型枚举 0 代付提现 1 下单
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置回调类型枚举 0 代付提现 1 下单
     *
     * @param type 回调类型枚举 0 代付提现 1 下单
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取处理类型 0实现类 1通用配置
     *
     * @return process_type - 处理类型 0实现类 1通用配置
     */
    public Integer getProcessType() {
        return processType;
    }

    /**
     * 设置处理类型 0实现类 1通用配置
     *
     * @param processType 处理类型 0实现类 1通用配置
     */
    public void setProcessType(Integer processType) {
        this.processType = processType;
    }

    /**
     * 获取成功返回字符串
     *
     * @return success_response - 成功返回字符串
     */
    public String getSuccessResponse() {
        return successResponse;
    }

    /**
     * 设置成功返回字符串
     *
     * @param successResponse 成功返回字符串
     */
    public void setSuccessResponse(String successResponse) {
        this.successResponse = successResponse;
    }

    /**
     * 获取失败返回字符串
     *
     * @return error_response - 失败返回字符串
     */
    public String getErrorResponse() {
        return errorResponse;
    }

    /**
     * 设置失败返回字符串
     *
     * @param errorResponse 失败返回字符串
     */
    public void setErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
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
     * 获取创建人
     *
     * @return create_user_id - 创建人
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人
     *
     * @param createUserId 创建人
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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