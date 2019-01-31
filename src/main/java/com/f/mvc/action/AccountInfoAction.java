package com.f.mvc.action;

import com.f.base.BaseAction;
import com.f.core.PageInfo;
import com.f.enums.StateEnum;
import com.f.mvc.entity.AccountInfo;
import com.f.mvc.entity.Channel;
import com.f.mvc.service.AccountInfoService;
import com.f.mvc.service.ChanelService;
import com.f.utils.StringUtils;
import com.f.validation.CreateGroup;
import com.f.validation.UpdateGroup;
import com.f.vo.Page;
import com.f.vo.ResponseVo;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author rebysfu@gmail.com
 * @description： 第三方后台账户信息管理
 * @create 2019-01-05 下午2:03
 **/
@RestController
@RequestMapping("/account")
@Api(value = "/account", description = "第三方账号管理")
public class AccountInfoAction extends BaseAction {
    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private ChanelService chanelService;

    @ApiOperation(value = "查询所有账号信息", notes = "查询所有账号信息")
    @PostMapping("/query")
    public ResponseVo list(@RequestBody Page page) {
        Example example = new Example(AccountInfo.class);
        example.setOrderByClause("create_time");
        example.excludeProperties();
        if (StringUtils.isNotEmpty(page.getKeyword())) {
            example.createCriteria().orLike("accountId", page.getKeyword() + "%");
        }
        PageInfo pageInfo = accountInfoService.queryPageListByExample(example, page.getPageNum(), page.getPageSize());
        return ResponseVo.builder().data(pageInfo).build();
    }

    @ApiOperation(value = "新增第三方账号信息", notes = "新增的id值不需要传入后台")
    @PostMapping("/add")
    public ResponseVo add(@RequestBody @Validated(CreateGroup.class) AccountInfoVO accountInfoVO) {
        Channel channel = new Channel();
        channel.setChannelId(accountInfoVO.getChannelId());
        Channel checkchannel = chanelService.queryOne(channel);
        if (checkchannel == null) {
            ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        }
        if (accountInfoVO.getStatus() != null && !StateEnum.isValidcode(accountInfoVO.getStatus())) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("状态值不正确").build();
        }
        AccountInfo info = new AccountInfo();
        info.setAccountId(accountInfoVO.getAccountid());
        info.setChannelId(accountInfoVO.getChannelId());
        AccountInfo acctount = accountInfoService.queryOne(info);
        if (acctount != null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("渠道账号已存在").build();
        }
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setChannelId(accountInfoVO.getChannelId());
        accountInfo.setAccountId(accountInfoVO.getAccountid());
        accountInfo.setPassword(accountInfoVO.getPassword());
        accountInfo.setCreateTime(new Date());
        accountInfo.setCreateUserId(getUserId());
        accountInfoService.save(accountInfo);
        return ResponseVo.builder().data(accountInfo).build();
    }

    @ApiOperation(value = "修改密码", notes = "修改账号信息，需要传入主键id，只允许修改密码")
    @PostMapping("/update")
    public ResponseVo update(@RequestBody @Validated(UpdateGroup.class) AccountInfoVO accountInfoVO) {
        if (accountInfoVO.getStatus() != null && !StateEnum.isValidcode(accountInfoVO.getStatus())) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("状态值不正确").build();
        }
        AccountInfo accountInfo = accountInfoService.queryById(accountInfoVO.getId());
        if (accountInfo == null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("账号信息不存在").build();
        }
        accountInfo.setPassword(accountInfoVO.getPassword());
        accountInfo.setModifyUserId(getUserId());
        accountInfo.setUpdateTime(new Date());
        //accountInfo.setStatus(accountInfoVO.getStatus());
        accountInfoService.update(accountInfo);
        return ResponseVo.builder().data(accountInfo).build();
    }

    @ApiOperation(value = "停用第三方账号", notes = "删除账号信息，这里删除做逻辑删除")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "path")
    @DeleteMapping("/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        AccountInfo accountInfo = accountInfoService.queryById(id);
        if (accountInfo == null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        }
        accountInfo.setStatus(StateEnum.DISABLED.getCode());
        accountInfoService.update(accountInfo);
        return ResponseVo.builder().code(HttpStatus.OK).build();
    }


    @ApiModel(value = "第三方账号信息")
    @Data
    static class AccountInfoVO {
        @ApiModelProperty(value = "主键id", notes = "更新数据时候不能为空")
        @NotNull(message = "主键id不能为空", groups = UpdateGroup.class)
        private Long id;

        /**
         * 第三方后台登陆账号
         */
        @ApiModelProperty(value = "第三方后台登陆账号", notes = "第三方后台登陆账号不允许修改")
        @NotNull(message = "第三方后台登陆账号不能为空", groups = {CreateGroup.class})
        private String accountid;

        /**
         * 第三方登陆密码
         */
        @ApiModelProperty(value = "第三方登陆密码")
        @NotNull(message = "第三方登陆密码不能为空", groups = {CreateGroup.class})
        private String password;

        /**
         * 渠道编码
         */
        @ApiModelProperty(value = "渠道编码", notes = "渠道编码不允许修改")
        @NotNull(message = "渠道编码不能为空", groups = {UpdateGroup.class, CreateGroup.class})
        private String channelId;

        /**
         * 状态 0 启用  1 停用
         */
        @ApiModelProperty(value = "账号状态", notes = "0 启用  1 停用")
        private Integer status = 0;
    }

}
