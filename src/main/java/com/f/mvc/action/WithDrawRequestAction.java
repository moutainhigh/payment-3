package com.f.mvc.action;

import com.f.base.BaseAction;
import com.f.enums.PayInterfaceType;
import com.f.enums.StateEnum;
import com.f.mvc.entity.WithDrawRequest;
import com.f.mvc.service.WithDrawRequestService;
import com.f.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by CodeGenerator on 2019/01/16.
 */
/*
 * @Author rebysfu@gmail.com
 * @Description 渠道请求配置
 * @Date 下午5:21 2019/1/16
 **/
@Api(value = "渠道请求信息管理", description = "渠道请求信息管理")
@RestController
@RequestMapping("/request")
public class WithDrawRequestAction extends BaseAction {
    @Autowired
    private WithDrawRequestService withDrawRequestService;

    @ApiOperation(value = "根据渠道Id查询渠道请求信息", notes = "查询渠道请求，必须传入渠道id")
    @GetMapping("/{channleId}")
    public ResponseVo list(@PathVariable String channleId) {
        WithDrawRequest withDrawRequest = new WithDrawRequest();
        withDrawRequest.setChannelId(channleId);
        List list = withDrawRequestService.queryListByWhere(withDrawRequest);
        return ResponseVo.builder().data(list).build();
    }

    @ApiOperation(value = "新增渠道请求", notes = "格式：\n {\"channelId\": \"1005\",\"parseType\": \"xml\",\"requestMethod\": \"http\",\"status\": 0,\"type\": 0,\"url\": \"string\"}")
    @PostMapping("/add")
    public ResponseVo save(@RequestBody @Validated CreateWithDrawRequestVO withDrawRequest) {
        //通过渠道id和请求类型确认唯一
        WithDrawRequest drawRequest = new WithDrawRequest();
        drawRequest.setChannelId(withDrawRequest.getChannelId());
        drawRequest.setType(withDrawRequest.getType());
        WithDrawRequest info = withDrawRequestService.queryOne(drawRequest);
        if (info != null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("记录已存在").build();
        }
        if (PayInterfaceType.isValidcode(withDrawRequest.getType()+"")) {
            drawRequest.setStatus(withDrawRequest.getStatus());
        } else {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("type 类型值不正确").build();
        }
        if (withDrawRequest.getStatus() != null) {
            if (StateEnum.isValidcode(withDrawRequest.getStatus())) {
                drawRequest.setStatus(withDrawRequest.getStatus());
            } else {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("状态值不正确").build();
            }
        }
        drawRequest.setParseType(withDrawRequest.getParseType());
        drawRequest.setRequestMethod(withDrawRequest.getRequestMethod());
        drawRequest.setUrl(withDrawRequest.getUrl());
        Date dateTime = new Date();
        drawRequest.setCreateTime(dateTime);
        drawRequest.setCreateUserId(getUserId());
        drawRequest.setUpdateTime(dateTime);
        drawRequest.setModifyUserId(getUserId());
        withDrawRequestService.save(drawRequest);
        return ResponseVo.builder().data(drawRequest).build();
    }

    @ApiOperation(value = "更新渠道请求", notes = "格式：\n {\"id\": 0,\"parseType\": \"string\",\"requestMethod\": \"string\",\"status\": 0,\"url\": \"string\"}")
    @PostMapping("/update")
    public ResponseVo update(@RequestBody @Validated UpdateWithDrawRequestVO withDrawRequest) {
        WithDrawRequest drawRequest = withDrawRequestService.queryById(withDrawRequest.getId());
        if (drawRequest == null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        }
        if (withDrawRequest.getStatus() != null) {
            if (StateEnum.isValidcode(withDrawRequest.getStatus())) {
                drawRequest.setStatus(withDrawRequest.getStatus());
            } else {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
            }
        }
        drawRequest.setUrl(withDrawRequest.getUrl());
        drawRequest.setParseType(withDrawRequest.getParseType());
        drawRequest.setRequestMethod(withDrawRequest.getRequestMethod());
        drawRequest.setModifyUserId(getUserId());
        drawRequest.setUpdateTime(new Date());
        withDrawRequestService.updateSelective(drawRequest);
        return ResponseVo.builder().data(drawRequest).build();
    }


    @ApiOperation(value = "删除渠道请求", notes = "")
    @DeleteMapping("/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        if (!withDrawRequestService.existsWithPrimaryKey(id)) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("记录不存在").build();
        }
        int row = withDrawRequestService.deleteById(id);
        if (row < 0) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseVo.builder().build();
    }

    @ApiModel(value = "更新渠道请求")
    @Data
    public static class UpdateWithDrawRequestVO {
        @ApiModelProperty(value = "主键id")
        @NotNull(message = "主键id不能为空")
        private Long id;
        /**
         * 请求方法
         */
        @ApiModelProperty(value = "请求方法")
        @NotNull(message = "请求方法不能为空")
        private String requestMethod;

        /**
         * url
         */
        @ApiModelProperty(value = "url")
        @NotNull(message = "url不能为空")
        private String url;

        /**
         * 返回值解析方式 枚举 正则 spel 脚本 html
         */
        @ApiModelProperty(value = "返回值解析方式 枚举 正则 spel 脚本 html")
        @NotNull(message = "返回值解析方式不能为空")
        private String parseType;

        /**
         * 状态 0 启用 1 停用
         */
        @ApiModelProperty(value = " 状态 0 启用 1 停用")
        private Integer status;
    }

    @ApiModel(value = "新增渠道请求")
    @Data
    public static class CreateWithDrawRequestVO {
        /**
         * 渠道编码
         */
        @ApiModelProperty(value = "渠道编码")
        @NotNull(message = "渠道编码不能为空")
        private String channelId;

        /**
         * 请求类型枚举 0 代付提现  1 账号余额查询  2 提现结果查询 3 下单
         */
        @ApiModelProperty(value = "请求类型枚举 0 代付提现  1 账号余额查询  2 提现结果查询 3 下单")
        @NotNull(message = "请求类型不能为空")
        private Integer type;
        /**
         * 请求方法
         */
        @ApiModelProperty(value = "请求方法")
        @NotNull(message = "请求方法不能为空")
        private String requestMethod;

        /**
         * url
         */
        @ApiModelProperty(value = "url")
        @NotNull(message = "url不能为空")
        private String url;

        /**
         * 返回值解析方式 枚举 正则 spel 脚本 html
         */
        @ApiModelProperty(value = "返回值解析方式 枚举 正则 spel 脚本 html")
        @NotNull(message = "返回值解析方式不能为空")
        private String parseType;

        /**
         * 状态 0 启用 1 停用
         */
        @ApiModelProperty(value = " 状态 0 启用 1 停用")
        private Integer status;


    }

}
