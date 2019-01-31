package com.f.mvc.action;

import com.f.base.BaseAction;
import com.f.enums.FieldType;
import com.f.enums.StateEnum;
import com.f.mvc.entity.PaymentCallbackParam;
import com.f.mvc.service.PaymentCallbackParamService;
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
 */
@Api(value = "/payment/callback", description = "回调参数配置信息管理")
@RestController
@RequestMapping("/payment/callback/param")
public class PaymentCallbackParamAction extends BaseAction {
    @Autowired
    private PaymentCallbackParamService paymentCallbackParamService;
    @Autowired
    private PaymentCallbackService paymentCallbackService;

    @ApiOperation(value = "查询回调配置参数")
    @GetMapping("/list/{callbackId}")
    public ResponseVo list(@PathVariable Long callbackId) {
        PaymentCallbackParam queryParam = new PaymentCallbackParam();
        queryParam.setCallbackId(callbackId);
        List list = paymentCallbackParamService.queryListByWhere(queryParam);
        return ResponseVo.builder().data(list).build();
    }

    @ApiOperation(value = "新增回调配置参数")
    @PostMapping("/add")
    public ResponseVo save(@RequestBody @Validated CreateCallbackParamVO callbackParamVO) {
        boolean statusExist = false;
        if (Optional.ofNullable(callbackParamVO.getStatus()).isPresent()) {
            if (StateEnum.isValidcode(callbackParamVO.getStatus())) {
                statusExist = true;
            } else {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("状态值不正确").build();
            }
        }
        if (!paymentCallbackService.existsWithPrimaryKey(callbackParamVO.getCallbackId())) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("回调配置信息不存在").build();
        }
        //回调参数固定参数需要判断key是否相同，如果key相同说明配置异常。
        PaymentCallbackParam queryParam = new PaymentCallbackParam();
        queryParam.setCallbackId(callbackParamVO.getCallbackId());
        queryParam.setFieldType(callbackParamVO.getFieldType());
        if(FieldType.FIX.equals(callbackParamVO.getFieldKey())){
            queryParam.setFieldKey(callbackParamVO.getFieldKey());
        }
        PaymentCallbackParam paymentCallbackParam = paymentCallbackParamService.queryOne(queryParam);
        if (Optional.ofNullable(paymentCallbackParam).isPresent()) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("参数类型:" + FieldType.valueOf(callbackParamVO.getFieldType()).getDescription()
                    + "参数key" + callbackParamVO.fieldKey + "记录已经存在").build();
        }
        paymentCallbackParam = new PaymentCallbackParam();
        BeanUtils.copyProperties(callbackParamVO, paymentCallbackParam, "status");
        paymentCallbackParam.setUpdateTime(new Date());
        paymentCallbackParam.setModifyUserId(getUserId());
        if (statusExist) {
            paymentCallbackParam.setStatus(callbackParamVO.getStatus());
        }
        paymentCallbackParamService.save(paymentCallbackParam);
        return ResponseVo.builder().data(paymentCallbackParam).build();
    }

    @ApiOperation(value = "修改回调配置参数")
    @PostMapping("/update")
    public ResponseVo update(@RequestBody @Validated UpdateCallbackParamVO callbackParamVO) {
        boolean statusExist = false;
        if (Optional.ofNullable(callbackParamVO.getStatus()).isPresent()) {
            if (StateEnum.isValidcode(callbackParamVO.getStatus())) {
                statusExist = true;
            } else {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("状态值不正确").build();
            }
        }
        PaymentCallbackParam paymentCallbackParam = paymentCallbackParamService.queryById(callbackParamVO.getId());
        if (!Optional.ofNullable(paymentCallbackParam).isPresent()) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        }

        if (paymentCallbackParam.getFieldType().equals(callbackParamVO.getFieldType())) {
            PaymentCallbackParam queryParam = new PaymentCallbackParam();
            queryParam.setCallbackId(paymentCallbackParam.getCallbackId());
            queryParam.setFieldType(callbackParamVO.getFieldType());
            queryParam.setFieldKey(callbackParamVO.getFieldKey());
            PaymentCallbackParam info = paymentCallbackParamService.queryOne(queryParam);
            if (Optional.ofNullable(info).isPresent()) {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("参数类型:" + FieldType.valueOf(callbackParamVO.getFieldType()).getDescription()
                        + "参数key" + callbackParamVO.fieldKey + "记录已经存在").build();
            }
        }

        BeanUtils.copyProperties(callbackParamVO, paymentCallbackParam, "status");
        paymentCallbackParam.setModifyUserId(getUserId());
        paymentCallbackParam.setUpdateTime(new Date());
        if (statusExist) {
            paymentCallbackParam.setStatus(callbackParamVO.getStatus());
        }
        paymentCallbackParamService.update(paymentCallbackParam);
        return ResponseVo.builder().build();
    }

    @ApiOperation(value = "删除回调配置参数")
    @DeleteMapping("/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        if (!paymentCallbackParamService.existsWithPrimaryKey(id)) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        }
        int row = paymentCallbackParamService.deleteById(id);
        if (row < 1) {
            return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseVo.builder().build();
    }

    @ApiModel(value = "新增回调参数")
    @Data
    static class CreateCallbackParamVO {

        /**
         * 回调id
         */
        @ApiModelProperty(value = "回调id")
        @NotNull(message = "回调id不能为空")
        private Long callbackId;

        /**
         * 参数key
         */
        @ApiModelProperty(value = "参数key")
        @NotNull(message = "参数key不能为空")
        private String fieldKey;

        /**
         * 参数类型 枚举 mchid orderid等
         */
        @ApiModelProperty(value = "参数类型")
        @NotNull(message = "参数类型不能为空")
        private String fieldType;

        /**
         * 参数顺序
         */
        @ApiModelProperty(value = "参数顺序")
        @NotNull(message = "参数顺序不能为空")
        private Integer fieldOrder;

        /**
         * 期望值
         */
        @ApiModelProperty(value = "期望值")
        private String assertValue;

        /**
         * 状态 0 启用 1 停用
         */
        private Integer status;

    }

    @ApiModel(value = "修改回调参数")
    @Data
    static class UpdateCallbackParamVO {
        @ApiModelProperty(value = "主键")
        @NotNull(message = "主键不能为空")
        private Long id;
        /**
         * 参数key
         */
        @ApiModelProperty(value = "参数key")
        @NotNull(message = "参数key不能为空")
        private String fieldKey;

        /**
         * 参数类型 枚举 mchid orderid等
         */
        @ApiModelProperty(value = "参数类型")
        @NotNull(message = "参数类型不能为空")
        private String fieldType;

        /**
         * 参数顺序
         */
        @ApiModelProperty(value = "参数顺序")
        @NotNull(message = "参数顺序不能为空")
        private Integer fieldOrder;

        /**
         * 期望值
         */
        @ApiModelProperty(value = "期望值")
        private String assertValue;

        /**
         * 状态 0 启用 1 停用
         */
        private Integer status;
    }

}
