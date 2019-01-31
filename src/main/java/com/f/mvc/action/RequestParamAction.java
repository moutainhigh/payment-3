package com.f.mvc.action;

import com.f.base.BaseAction;
import com.f.enums.FieldType;
import com.f.enums.StateEnum;
import com.f.mvc.entity.RequestParam;
import com.f.mvc.service.RequestParamService;
import com.f.mvc.service.WithDrawRequestService;
import com.f.validation.CreateGroup;
import com.f.validation.UpdateGroup;
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

/**
 * Created by CodeGenerator on 2019/01/16.
 */

@Api(value = "/request/param", description = "渠道接口请求参数管理")
@RestController
@RequestMapping("/request/param")
public class RequestParamAction extends BaseAction {
    @Autowired
    private RequestParamService requestParamService;
    @Autowired
    private WithDrawRequestService withDrawRequestService;

    @ApiOperation(value = "查询渠道请求参数列表", notes = "查询渠道请求参数列表")
    @GetMapping("/{requestId}")
    public ResponseVo list(@PathVariable Long requestId) {
        RequestParam param = new RequestParam();
        param.setRequestId(requestId);
        List list = requestParamService.queryListByWhere(param);
        return ResponseVo.builder().data(list).build();
    }

    @ApiOperation(value = "新增渠道请求参数列表", notes = "新增渠道请求参数格式：\n {\"defaultValue\":\"1004\",\"fieldKey\":\"appid\",\"fieldOrder\":1,\"fieldType\":\"string\",\"status\":0}")
    @PostMapping("/add")
    public ResponseVo save(@RequestBody @Validated(CreateGroup.class) RquestParamsVO rquestParamsVO) {
        RequestParam requestParam = new RequestParam();
        if (rquestParamsVO.getStatus() != null) {
            if (StateEnum.isValidcode(rquestParamsVO.getStatus())) {
                requestParam.setStatus(rquestParamsVO.getStatus());
            } else {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
            }
        }
        if (!withDrawRequestService.existsWithPrimaryKey(rquestParamsVO.getRequestId())) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("渠道" + FieldType.valueOf(rquestParamsVO.getFieldType()).name() + "请求配置不存在").build();
        }

        RequestParam query = new RequestParam();
        query.setRequestId(rquestParamsVO.getRequestId());
        query.setFieldType(rquestParamsVO.getFieldType());
        if (rquestParamsVO.getFieldType().equals(FieldType.FIX.name())) {
            query.setFieldKey(rquestParamsVO.getFieldKey());
        }
        RequestParam info = requestParamService.queryOne(query);
        if (info != null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("参数类型:" + FieldType.valueOf(rquestParamsVO.getFieldType()).getDescription()
                    + "参数key" + rquestParamsVO.fieldKey + "记录已经存在,不允许新增").build();
        }
        BeanUtils.copyProperties(rquestParamsVO, requestParam);
        Date dateTime = new Date();
        requestParam.setUpdateTime(dateTime);
        requestParam.setModifyUserId(getUserId());
        requestParam.setModifyUserId(getUserId());
        requestParam.setUpdateTime(dateTime);
        requestParamService.save(requestParam);
        return ResponseVo.builder().data(requestParam).build();
    }

    @ApiOperation(value = "更新渠道请求参数信息", notes = "修改渠道请求参数格式：\n {\"id\":1,\"defaultValue\":\"1004\",\"fieldKey\":\"appid\",\"fieldOrder\":1,\"fieldType\":\"string\",\"status\":0}")
    @PostMapping("/update")
    public ResponseVo update(@RequestBody @Validated(UpdateGroup.class) RquestParamsVO requestParamVo) {
        RequestParam requestParam = requestParamService.queryById(requestParamVo.getId());
        if (requestParam == null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        }
        if (requestParamVo.getStatus() != null) {
            if (StateEnum.isValidcode(requestParamVo.getStatus())) {
                requestParam.setStatus(requestParamVo.getStatus());
            } else {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
            }
        }
        if (!requestParam.getFieldType().equals(requestParamVo.getFieldType())) {
            RequestParam query = new RequestParam();
            query.setRequestId(requestParamVo.getRequestId());
            query.setFieldType(requestParam.getFieldType());//修改商户不允许修改字段类型
            if (requestParamVo.getFieldType().equals(FieldType.FIX.name())) {
                query.setFieldKey(requestParamVo.getFieldKey());

            }
            RequestParam info = requestParamService.queryOne(query);
            if (info != null) {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("参数类型:" + FieldType.valueOf(requestParamVo.getFieldType()).getDescription()
                        + "参数key" + requestParamVo.fieldKey + "记录已经存在,不允许修改").build();
            }
        }
        requestParam.setDefaultValue(requestParamVo.getDefaultValue());
        requestParam.setFieldKey(requestParamVo.getFieldKey());
        requestParam.setFieldOrder(requestParamVo.getFieldOrder());
        requestParam.setFieldType(requestParamVo.getFieldType());
        requestParam.setModifyUserId(getUserId());
        requestParam.setUpdateTime(new Date());
        requestParamService.update(requestParam);
        return ResponseVo.builder().data(requestParam).build();
    }

    @ApiOperation(value = "删除渠道请求参数信息")
    @DeleteMapping("/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        boolean extis = requestParamService.existsWithPrimaryKey(id);
        if (!extis) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        }
        requestParamService.deleteById(id);
        return ResponseVo.builder().build();
    }

    @ApiModel(value = "更新请求参数")
    @Data
    public static class RquestParamsVO {
        @ApiModelProperty("主键id")
        @NotNull(message = "主键id不能为空", groups = UpdateGroup.class)
        private Long id;

        /**
         * 请求id
         */
        @ApiModelProperty("请求id")
        @NotNull(message = "请求id不能为空", groups = CreateGroup.class)
        private Long requestId;
        /**
         * 参数key
         */
        @ApiModelProperty("参数key")
        @NotNull(message = "参数key不能为空", groups = {CreateGroup.class, UpdateGroup.class})
        private String fieldKey;

        /**
         * 参数类型 枚举 mchid orderid等
         */
        @ApiModelProperty("参数类型")
        @NotNull(message = "参数类型不能为空", groups = {CreateGroup.class, UpdateGroup.class})
        private String fieldType;

        /**
         * 默认值
         */
        @ApiModelProperty("默认值")
        private String defaultValue;

        /**
         * 参数顺序
         */
        @ApiModelProperty("参数顺序")
        @NotNull(message = "参数顺序不能为空", groups = CreateGroup.class)
        private Integer fieldOrder;
        /**
         * 状态 0 启用 1 停用
         */
        @ApiModelProperty("状态 0 启用 1 停用")
        private Integer status;
    }

}
