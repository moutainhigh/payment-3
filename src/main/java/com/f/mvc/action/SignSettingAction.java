package com.f.mvc.action;

import com.f.base.BaseAction;
import com.f.core.PageInfo;
import com.f.enums.SignConfigTypeEnum;
import com.f.enums.SignType;
import com.f.mvc.entity.Channel;
import com.f.mvc.entity.SignSetting;
import com.f.mvc.service.ChanelService;
import com.f.mvc.service.SignSettingService;
import com.f.mvc.service.WithDrawRequestService;
import com.f.utils.StringUtils;
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
import java.util.Optional;

/**
 * Created by CodeGenerator on 2019/01/17.
 */
@Api(value = "/sign/setting",description = "渠道接口验签配置")
@RestController
@RequestMapping("/sign/setting")
public class SignSettingAction extends BaseAction {
    @Autowired
    private SignSettingService signSettingService;
    @Autowired
    private WithDrawRequestService withDrawRequestService;
    @Autowired
    private ChanelService chanelService;

    @ApiOperation(value = "查询渠道签名配置")
    @PostMapping(value = "/query")
    public ResponseVo list(@RequestBody SearchSignSettingVo searchSignSettingVo) {
        Example example = new Example(SignSetting.class);
        example.createCriteria().andEqualTo("type", searchSignSettingVo.getType());
        if (StringUtils.isNotEmpty(searchSignSettingVo.getKeyword())) {
            example.createCriteria().andLike("foreignKey", searchSignSettingVo.getKeyword());
        }
        PageInfo pageInfo = signSettingService.queryPageListByExample(example, searchSignSettingVo.getPageNum(), searchSignSettingVo.getPageSize());
        return ResponseVo.builder().data(pageInfo).build();
    }

    @ApiOperation(value = "新增渠道签名配置")
    @PostMapping("/add")
    public ResponseVo save(@RequestBody @Validated CreateSignSettingVo signSettingVo) {
        if (!SignConfigTypeEnum.isValidcode(signSettingVo.getType()+"")) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("签名配置类型错误").build();
        }
        if (!SignType.isValidcode(signSettingVo.getSignType())) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("签名方式类型错误").build();
        }
        if ((signSettingVo.getType().equals(SignConfigTypeEnum.REQUEST.getCode())) || (signSettingVo.getType() .equals (SignConfigTypeEnum.CALLBACK.getCode()))) {//单个请求/回调加密
            if (!withDrawRequestService.existsWithPrimaryKey(Long.parseLong(signSettingVo.getForeignKey()))) {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("渠道请求配置不存在").build();
            }
        } else if (signSettingVo.getType().equals (SignConfigTypeEnum.CHANNEL.getCode())) {//渠道
            Channel channel=new Channel();
            channel.setChannelId(signSettingVo.getForeignKey());
            if (chanelService.queryOne(channel)==null) {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("渠道信息不存在").build();
            }
        }

        SignSetting queryParam = new SignSetting();
        queryParam.setType(signSettingVo.getType());
        queryParam.setForeignKey(signSettingVo.getForeignKey());
        SignSetting info = signSettingService.queryOne(queryParam);
        if (info != null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("记录已存在").build();
        }
        SignSetting setting = new SignSetting();
        BeanUtils.copyProperties(signSettingVo, setting);
        signSettingService.save(setting);
        return ResponseVo.builder().data(setting).build();
    }

    @ApiOperation(value = "更新渠道签名配置")
    @PostMapping("/update")
    public ResponseVo update(@RequestBody @Validated UpdateSignSettingVo signSettingVo) {
        SignSetting setting = signSettingService.queryById(signSettingVo.getId());
        if (!Optional.ofNullable(setting).isPresent()) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        }
        BeanUtils.copyProperties(signSettingVo, setting);
        signSettingService.update(setting);
        return ResponseVo.builder().build();
    }

    @ApiOperation(value = "删除渠道签名配置")
    @DeleteMapping("/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        if (!signSettingService.existsWithPrimaryKey(id)) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        }
        int row = signSettingService.deleteById(id);
        if (row < 1) {
            return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseVo.builder().build();
    }

    @ApiModel(value = "配置查询参数")
    @Data
    static class SearchSignSettingVo extends Page {
        /**
         * 配置类型 0渠道配置 1单个请求配置 2回调配置
         */
        @NotNull(message = "配置类型不能为空")
        private Integer type;
    }

    @ApiModel(value = "签名配置")
    @Data
    static class CreateSignSettingVo {
        /**
         * 配置类型 0渠道配置 1单个请求配置 2回调配置
         */
        @ApiModelProperty(value = "配置类型 0渠道配置 1单个请求配置 2回调配置")
        @NotNull(message = "配置类型不能为空")
        private Integer type;

        /**
         * 外键 渠道id、请求id
         */
        @ApiModelProperty(value = "外键 渠道id、请求id")
        @NotNull(message = "外键 渠道id、请求id 不能为空")
        private String foreignKey;

        /**
         * 签名类型 枚举
         */
        @ApiModelProperty(value = "签名方式")
        @NotNull(message = "签名方式不能为空")
        private String signType;

        /**
         * 原串是否包含key
         */
        @ApiModelProperty(value = "原串是否包含key 默认包含")
        private Boolean containsKey = true;

        /**
         * key与value间隔符
         */
        @ApiModelProperty(value = "key与value间隔符")
        private String keyValueSplit = "=";

        /**
         * value之间间隔符
         */
        @ApiModelProperty(value = "value之间间隔符")
        private String valueSplit = "&";

        /**
         * 商户key前缀
         */
        @ApiModelProperty(value = "商户key前缀")
        private String keyPrefix;

        /**
         * key是否在前面
         */
        @ApiModelProperty(value = "key是否在前面")
        private Boolean keyFirst;

        /**
         * 转换大写
         */
        @ApiModelProperty(value = "转换大写")
        private Boolean upperCase;

        /**
         * 额外配置 json
         */
        @ApiModelProperty(value = "额外配置 json")
        private String additionSetting;
    }

    @ApiModel(value = "修改签名配置")
    @Data
    static class UpdateSignSettingVo {
        @ApiModelProperty(value = "配置类型 0渠道配置 1单个请求配置 2回调配置")
        @NotNull(message = "配置类型不能为空")
        private Long id;
        /**
         * 配置类型 0渠道配置 1单个请求配置 2回调配置
         */
    /*    @ApiModelProperty(value = "配置类型 0渠道配置 1单个请求配置 2回调配置")
        @NotNull(message = "配置类型不能为空")
        private Integer type;
*/
        /**
         * 外键 渠道id、请求id
         */
      /*  @ApiModelProperty(value = "外键 渠道id、请求id")
        @NotNull(message = "外键 渠道id、请求id 不能为空")
        private Long foreignKey;*/

        /**
         * 签名类型 枚举
         */
        @ApiModelProperty(value = "签名方式")
        @NotNull(message = "签名方式不能为空")
        private String signType;

        /**
         * 原串是否包含key
         */
        @ApiModelProperty(value = "原串是否包含key 默认包含")
        private Boolean containsKey = true;

        /**
         * key与value间隔符
         */
        @ApiModelProperty(value = "key与value间隔符")
        private String keyValueSplit = "=";

        /**
         * value之间间隔符
         */
        @ApiModelProperty(value = "value之间间隔符")
        private String valueSplit = "&";

        /**
         * 商户key前缀
         */
        @ApiModelProperty(value = "商户key前缀")
        private String keyPrefix;

        /**
         * key是否在前面
         */
        @ApiModelProperty(value = "key是否在前面")
        private Boolean keyFirst;

        /**
         * 转换大写
         */
        @ApiModelProperty(value = "转换大写")
        private Boolean upperCase;

        /**
         * 额外配置 json
         */
        @ApiModelProperty(value = "额外配置 json")
        private String additionSetting;
    }

}
