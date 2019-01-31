package com.f.mvc.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tbl_request")
public class WithDrawRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 渠道编码
     */
    @Column(name = "channel_id")
    private String channelId;

    /**
     * 请求类型枚举 0 代付提现  1 账号余额查询  2 提现结果查询 3 下单
     */
    private Integer type;

    /**
     * 请求方法 
     */
    @Column(name = "request_method")
    private String requestMethod;

    /**
     * url 
     */
    private String url;

    /**
     * 返回值解析方式 枚举 正则 spel 脚本 html
     */
    @Column(name = "parse_type")
    private String parseType;

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
     * 获取请求类型枚举 0 代付提现  1 账号余额查询  2 提现结果查询 3 下单
     *
     * @return type - 请求类型枚举 0 代付提现  1 账号余额查询  2 提现结果查询 3 下单
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置请求类型枚举 0 代付提现  1 账号余额查询  2 提现结果查询 3 下单
     *
     * @param type 请求类型枚举 0 代付提现  1 账号余额查询  2 提现结果查询 3 下单
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取请求方法 
     *
     * @return request_method - 请求方法 
     */
    public String getRequestMethod() {
        return requestMethod;
    }

    /**
     * 设置请求方法 
     *
     * @param requestMethod 请求方法 
     */
    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    /**
     * 获取url 
     *
     * @return url - url 
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置url 
     *
     * @param url url 
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取返回值解析方式 枚举 正则 spel 脚本 html
     *
     * @return parse_type - 返回值解析方式 枚举 正则 spel 脚本 html
     */
    public String getParseType() {
        return parseType;
    }

    /**
     * 设置返回值解析方式 枚举 正则 spel 脚本 html
     *
     * @param parseType 返回值解析方式 枚举 正则 spel 脚本 html
     */
    public void setParseType(String parseType) {
        this.parseType = parseType;
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