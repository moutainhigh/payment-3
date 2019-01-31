package com.f.mvc.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tbl_channel_setting")
public class ChannelSetting {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 参数配置名称
     */
    private String name;

    /**
     * 渠道编码
     */
    @Column(name = "channel_id")
    private String channelId;

    /**
     * 接口类型 0 代付提现  1 账号余额查询  2 提现结果查询
     */
    private Integer type;

    /**
     *  金额计算单位  分 /元 
     */
    @Column(name = "amt_unit")
    private Integer amtUnit;

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
    private String createUserId;

    /**
     * 修改人
     */
    @Column(name = "modify_user_id")
    private String modifyUserId;

    /**
     * 状态 0 启用 1 停用
     */
    private Integer status;

    /**
     * 请求配置参数{
"parmas":[ {"filed":"sp_id","type":"string","required":true,"value":"1002","order":"1"}, {"filed":"merct_id","type":"string","required":false,"value":"20003","order":"2"}]
}
     */
    @Column(name = "request_params")
    private String requestParams;

    /**
     * 返回配置参数
     */
    @Column(name = "response_params")
    private String responseParams;

    /**
     * 加密参数
     */
    @Column(name = "sign_params")
    private String signParams;

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
     * 获取参数配置名称
     *
     * @return name - 参数配置名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置参数配置名称
     *
     * @param name 参数配置名称
     */
    public void setName(String name) {
        this.name = name;
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
     * 获取接口类型 0 代付提现  1 账号余额查询  2 提现结果查询
     *
     * @return type - 接口类型 0 代付提现  1 账号余额查询  2 提现结果查询
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置接口类型 0 代付提现  1 账号余额查询  2 提现结果查询
     *
     * @param type 接口类型 0 代付提现  1 账号余额查询  2 提现结果查询
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取 金额计算单位  分 /元 
     *
     * @return amt_unit -  金额计算单位  分 /元 
     */
    public Integer getAmtUnit() {
        return amtUnit;
    }

    /**
     * 设置 金额计算单位  分 /元 
     *
     * @param amtUnit  金额计算单位  分 /元 
     */
    public void setAmtUnit(Integer amtUnit) {
        this.amtUnit = amtUnit;
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
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人
     *
     * @param createUserId 创建人
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取修改人
     *
     * @return modify_user_id - 修改人
     */
    public String getModifyUserId() {
        return modifyUserId;
    }

    /**
     * 设置修改人
     *
     * @param modifyUserId 修改人
     */
    public void setModifyUserId(String modifyUserId) {
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

    /**
     * 获取请求配置参数{
"parmas":[ {"filed":"sp_id","type":"string","required":true,"value":"1002","order":"1"}, {"filed":"merct_id","type":"string","required":false,"value":"20003","order":"2"}]
}
     *
     * @return request_params - 请求配置参数{
"parmas":[ {"filed":"sp_id","type":"string","required":true,"value":"1002","order":"1"}, {"filed":"merct_id","type":"string","required":false,"value":"20003","order":"2"}]
}
     */
    public String getRequestParams() {
        return requestParams;
    }

    /**
     * 设置请求配置参数{
"parmas":[ {"filed":"sp_id","type":"string","required":true,"value":"1002","order":"1"}, {"filed":"merct_id","type":"string","required":false,"value":"20003","order":"2"}]
}
     *
     * @param requestParams 请求配置参数{
"parmas":[ {"filed":"sp_id","type":"string","required":true,"value":"1002","order":"1"}, {"filed":"merct_id","type":"string","required":false,"value":"20003","order":"2"}]
}
     */
    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    /**
     * 获取返回配置参数
     *
     * @return response_params - 返回配置参数
     */
    public String getResponseParams() {
        return responseParams;
    }

    /**
     * 设置返回配置参数
     *
     * @param responseParams 返回配置参数
     */
    public void setResponseParams(String responseParams) {
        this.responseParams = responseParams;
    }

    /**
     * 获取加密参数
     *
     * @return sign_params - 加密参数
     */
    public String getSignParams() {
        return signParams;
    }

    /**
     * 设置加密参数
     *
     * @param signParams 加密参数
     */
    public void setSignParams(String signParams) {
        this.signParams = signParams;
    }
}