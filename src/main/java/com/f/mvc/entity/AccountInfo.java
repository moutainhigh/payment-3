package com.f.mvc.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "tbl_account_info")
public class AccountInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 渠道编码
     */
    @Column(name = "channel_id")
    private String channelId;

    /**
     * 第三方后台登陆账号
     */
    @Column(name = "account_id")
    private String accountId;

    /**
     * 第三方登陆密码
     */
    private String password;

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
     * 状态 0 启用  1 停用
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
     * 获取第三方后台登陆账号
     *
     * @return account_id - 第三方后台登陆账号
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 设置第三方后台登陆账号
     *
     * @param accountId 第三方后台登陆账号
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取第三方登陆密码
     *
     * @return password - 第三方登陆密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置第三方登陆密码
     *
     * @param password 第三方登陆密码
     */
    public void setPassword(String password) {
        this.password = password;
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
     * 获取状态 0 启用  1 停用
     *
     * @return status - 状态 0 启用  1 停用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0 启用  1 停用
     *
     * @param status 状态 0 启用  1 停用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}