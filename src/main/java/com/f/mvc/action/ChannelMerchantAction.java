package com.f.mvc.action;

import com.f.base.BaseAction;
import com.f.core.PageInfo;
import com.f.enums.StateEnum;
import com.f.mvc.entity.Channel;
import com.f.mvc.entity.ChannelMerchant;
import com.f.mvc.service.ChanelService;
import com.f.mvc.service.ChannelMerchantService;
import com.f.utils.StringUtils;
import com.f.validation.CreateGroup;
import com.f.validation.UpdateGroup;
import com.f.vo.Page;
import com.f.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;

/**
 * Created by CodeGenerator on 2019/01/16.
 */
@Api(value = "/channel/merchant", description = "渠道和商户绑定管理")
@RestController
@RequestMapping("/channel/merchant")
public class ChannelMerchantAction extends BaseAction {
    @Autowired
    private ChannelMerchantService channelMerchantService;
    @Autowired
    private ChanelService chanelService;

    @ApiOperation(value = "查询渠道和商户绑定列表", notes = "查询渠道和商户绑定列表")
    @PostMapping("/list")
    public ResponseVo list(@RequestBody QueryChannelMerchant query) {
        Example example = new Example(ChannelMerchant.class);
        if (StringUtils.isNotEmpty(query.getKeyword())) {
            example.createCriteria().orLike("merchantId", query.getKeyword());
        }
        PageInfo list = channelMerchantService.queryPageListByExample(example, query.getPageNum(), query.getPageSize());
        return ResponseVo.builder().data(list).build();
    }

    @ApiOperation(value = "渠道和商户信息绑定", notes = "渠道和商户绑定")
    @PostMapping("/add")
    public ResponseVo save(@RequestBody @Validated(CreateGroup.class) ChannelMerchantVO channelMerchantVO) {
        Channel query = new Channel();
        query.setChannelId(channelMerchantVO.getChannelId());
        Channel channelInfo = chanelService.queryOne(query);
        if (channelInfo == null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("渠道信息不存在").build();
        }
        ChannelMerchant check = new ChannelMerchant();
        check.setChannelId(channelMerchantVO.getChannelId());
        check.setMerchantId(channelMerchantVO.getMerchantId());
        ChannelMerchant info = channelMerchantService.queryOne(check);
        if (info != null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("渠道和商户已绑定").build();
        }
        ChannelMerchant channelMerchant = new ChannelMerchant();
        if (channelMerchantVO.getStatus() != null) {
            if (StateEnum.isValidcode(channelMerchantVO.getStatus())) {
                channelMerchant.setStatus(channelMerchantVO.getStatus());
            } else {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
            }
        }
        BeanUtils.copyProperties(channelMerchantVO, channelMerchant);
        channelMerchant.setChannelName(channelInfo.getChannelName());
        channelMerchantService.save(channelMerchant);
        return ResponseVo.builder().data(channelMerchant).build();
    }

    @ApiOperation(value = "更新", notes = "根据主键更新商户渠道绑定关系，不允许修改渠道编码，其他都可以修改")
    @PostMapping("/update")
    public ResponseVo update(@RequestBody @Validated(UpdateGroup.class) ChannelMerchantVO channelMerchantVO) {
        ChannelMerchant merchant = channelMerchantService.queryById(channelMerchantVO.getId());
        if (merchant == null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        }
        if (!channelMerchantVO.getMerchantId().equals(merchant.getMerchantId())) {
            ChannelMerchant check = new ChannelMerchant();
            check.setChannelId(merchant.getChannelId());
            check.setMerchantId(channelMerchantVO.getMerchantId());
            ChannelMerchant merchantInfo = channelMerchantService.queryOne(check);
            if (merchantInfo != null) {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("修改的商户号，已经和渠道有绑定关系").build();
            }
        }
        if (channelMerchantVO.getStatus() != null) {
            if (StateEnum.isValidcode(channelMerchantVO.getStatus())) {
                merchant.setStatus(channelMerchantVO.getStatus());
            } else {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
            }
        }
        merchant.setAppkey(channelMerchantVO.getAppkey());
        merchant.setAccountNo(channelMerchantVO.getAccountNo());
        merchant.setAccountName(channelMerchantVO.getAccountName());
        merchant.setMerchantId(channelMerchantVO.getMerchantId());
        channelMerchantService.update(merchant);
        return ResponseVo.builder().build();
    }

    @ApiOperation(value = "删除", notes = "只允许删除停用的数据")
    @DeleteMapping("/{id}")
    public ResponseVo delete(@PathVariable Long id) {

        ChannelMerchant channelMerchant = channelMerchantService.queryById(id);
        if (channelMerchant == null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("数据不存在").build();
        }
        if (channelMerchant.getStatus().equals(StateEnum.NORMAL.getCode())) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("正常状态数据不允许删除").build();
        }
        channelMerchantService.deleteById(id);
        return ResponseVo.builder().build();
    }

    @ApiModel(value = "渠道商户绑定查询")
    @Data
    static class QueryChannelMerchant extends Page {
        /**
         * 渠道id
         */
        @ApiModelProperty(value = "渠道编码", example = "1", required = true)
        @NotNull(message = "渠道编码不能为空")
        private String channelId;
    }

    @ApiModel(value = "渠道商户绑定")
    @Data
    public static class ChannelMerchantVO {
        @ApiModelProperty(value = "主键", example = "1")
        @NotNull(message = "主键不能为空", groups = UpdateGroup.class)
        private Long id;
        /**
         * 渠道id
         */
        @ApiModelProperty(value = "渠道编码", example = "1")
        @NotNull(message = "渠道编码不能为空", groups = {CreateGroup.class})
        private String channelId;

        /**
         * 商户id
         */
        @ApiModelProperty(value = "商户号", required = true)
        @NotNull(message = "商户号不能为空", groups = {CreateGroup.class, UpdateGroup.class})
        private String merchantId;
        /**
         * 商户名称（可以是公司的名字skline）
         */
        @ApiModelProperty(value = "商户名称（可以是公司的名字skline")
        private String merchantName;
        /**
         * 收款账户号
         */
        @ApiModelProperty(value = "收款账户号")
        @NotNull(message = "收款账号不能为空", groups = {CreateGroup.class, UpdateGroup.class})
        private String accountNo;

        /**
         * 收款账户名
         */
        @ApiModelProperty(value = "收款账户名")
        @NotNull(message = "收款账户名不能为空", groups = {CreateGroup.class, UpdateGroup.class})
        private String accountName;

        /**
         * 商户公钥
         */
        @ApiModelProperty(value = "商户密钥", required = true)
        @NotNull(message = "商户密钥不能为空", groups = {CreateGroup.class, UpdateGroup.class})
        private String appkey;

        /**
         * 状态 0 启用 1 停用
         */
        @ApiModelProperty("状态 0 启用 1 停用")
        private Integer status;
    }

}
