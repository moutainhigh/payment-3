package com.f.mvc.action;

import com.f.base.BaseAction;
import com.f.enums.StateEnum;
import com.f.mvc.entity.PaymentCallback;
import com.f.mvc.service.PaymentCallbackService;
import com.f.utils.StringUtils;
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

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by CodeGenerator on 2019/01/22.
 * 支付回调接口（提供给渠道进行回调）
 */
@Api(value = "/payment/callback",description = "回调配置信息管理")
@RestController
@RequestMapping("/payment/callback")
public class PaymentCallbackAction extends BaseAction {
    @Autowired
    private PaymentCallbackService paymentCallbackService;

    @ApiOperation(value = "根据渠道Id查询回调配置信息")
    @GetMapping("/{channleId}")
    public ResponseVo query(@PathVariable String channleId) {
        PaymentCallback queryPaymentCallback = new PaymentCallback();
        queryPaymentCallback.setChannelId(channleId);
        List list = paymentCallbackService.queryListByWhere(queryPaymentCallback);
        return ResponseVo.builder().data(list).build();
    }

    @ApiOperation(value = "新增回调配置信息")
    @PostMapping("/add")
    public ResponseVo save(@RequestBody @Validated CreatePayMentCallBackVO createPayMentCallBackVO) {
        boolean statusExist = false;
        if (createPayMentCallBackVO.getStatus()!=null) {
            if (StateEnum.isValidcode(createPayMentCallBackVO.getStatus())) {
                statusExist = true;
            } else {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("状态值不正确").build();
            }
        }
        PaymentCallback query = new PaymentCallback();
        query.setChannelId(createPayMentCallBackVO.getChannelId());
        query.setType(createPayMentCallBackVO.getType());
        PaymentCallback paymentCallback = paymentCallbackService.queryOne(query);
        if (Optional.ofNullable(paymentCallback).isPresent()) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("").build();
        }
        Date time = new Date();
        paymentCallback = new PaymentCallback();
        BeanUtils.copyProperties(createPayMentCallBackVO, paymentCallback, "status");
        paymentCallback.setCreateTime(time);
        paymentCallback.setCreateUserId(getUserId());
        paymentCallback.setUpdateTime(time);
        paymentCallback.setModifyUserId(getUserId());
        if (statusExist) {
            paymentCallback.setStatus(createPayMentCallBackVO.getStatus());
        }
        paymentCallbackService.save(paymentCallback);
        return ResponseVo.builder().data(paymentCallback).build();
    }

    @ApiOperation(value = "修改回调配置信息")
    @PostMapping("/update")
    public ResponseVo update(@RequestBody @Validated UpdatePayMentCallBackVO payMentCallBackVO) {
        boolean statusExist = false;
        if (Optional.ofNullable(payMentCallBackVO).isPresent()) {
            if (StateEnum.isValidcode(payMentCallBackVO.getStatus())) {
                statusExist = true;
            } else {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("状态值不正确").build();
            }
        }
        PaymentCallback paymentCallback = paymentCallbackService.queryById(payMentCallBackVO.getId());
        if (!Optional.ofNullable(paymentCallback).isPresent()) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        }
        BeanUtils.copyProperties(payMentCallBackVO, paymentCallback, "status");
        paymentCallback.setModifyUserId(getUserId());
        paymentCallback.setUpdateTime(new Date());
        if (statusExist) {
            paymentCallback.setStatus(payMentCallBackVO.getStatus());
        }
        paymentCallbackService.update(paymentCallback);
        return ResponseVo.builder().data(paymentCallback).build();
    }

    @ApiOperation(value = "删除回调配置信息")
    @DeleteMapping("/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        if (!paymentCallbackService.existsWithPrimaryKey(id)) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        }
        int row = paymentCallbackService.deleteById(id);
        if (row < 1) {
            return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseVo.builder().build();
    }

    @ApiModel("新增回调配置")
    @Data
    static class CreatePayMentCallBackVO {
        /**
         * 渠道编码
         */
        @ApiModelProperty(value = "渠道编码")
        @NotNull(message = "渠道编码不能为空")
        private String channelId;

        /**
         * 回调类型枚举 0 代付提现 1 下单
         */
        @ApiModelProperty(value = "回调类型枚举 0 代付提现 1 下单")
        @NotNull(message = "回调类型不能为空")
        private Integer type;

        /**
         * 处理类型 0实现类 1通用配置
         */
        @ApiModelProperty(value = "处理类型 0实现类 1通用配置")
        @NotNull(message = "处理类型不能为空")
        private Integer processType;

        /**
         * 成功返回字符串
         */
        @ApiModelProperty(value = " 成功返回字符串")
        @NotNull(message = "成功返回字符串不能为空")
        private String successResponse;

        /**
         * 失败返回字符串
         */
        @ApiModelProperty(value = "失败返回字符串")
        @NotNull(message = "失败返回字符串不能为空")
        private String errorResponse;
        /**
         * 状态 0 启用 1 停用
         */
        private Integer status;
    }

    @ApiModel(value = "修改回调配置")
    @Data
    static class UpdatePayMentCallBackVO {
        @ApiModelProperty(value = "主键")
        @NotNull(message = "主键不能为空")
        private Long id;
        /**
         * 渠道编码
         */
       /* @ApiModelProperty(value = "渠道编码")
        @NotNull(message = "")
        private String channelId;*/

        /**
         * 回调类型枚举 0 代付提现 1 下单
         */
      /*  @ApiModelProperty(value = "回调类型枚举 0 代付提现 1 下单")
        @NotNull(message = "")
        private Integer type;*/

        /**
         * 处理类型 0实现类 1通用配置
         */
        @ApiModelProperty(value = "处理类型 0实现类 1通用配置")
        @NotNull(message = "处理类型不能为空")
        private Integer processType;

        /**
         * 成功返回字符串
         */
        @ApiModelProperty(value = " 成功返回字符串")
        @NotNull(message = "成功返回字符串不能为空")
        private String successResponse;

        /**
         * 失败返回字符串
         */
        @ApiModelProperty(value = "失败返回字符串")
        @NotNull(message = "失败返回字符串不能为空")
        private String errorResponse;
        /**
         * 状态 0 启用 1 停用
         */
        private Integer status;
    }
}
