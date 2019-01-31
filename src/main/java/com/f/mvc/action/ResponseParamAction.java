package com.f.mvc.action;

import com.f.base.BaseAction;
import com.f.enums.StateEnum;
import com.f.mvc.entity.ResponseParam;
import com.f.mvc.service.ResponseParamService;
import com.f.mvc.service.WithDrawRequestService;
import com.f.validation.CreateGroup;
import com.f.validation.UpdateGroup;
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
@Api(value = "/request/param", description = "渠道接口返回参数管理")
@RestController
@RequestMapping("/response/param")
public class ResponseParamAction extends BaseAction {
    @Autowired
    private ResponseParamService responseParamService;
    @Autowired
    private WithDrawRequestService withDrawRequestService;


    @ApiOperation(value = "查询渠道返回参数列表", notes = "查询渠道返回参数列表")
    @GetMapping("/{requestId}")
    public ResponseVo list(@PathVariable Long requestId) {
        ResponseParam param = new ResponseParam();
        param.setRequestId(requestId);
        List list = responseParamService.queryListByWhere(param);
        return ResponseVo.builder().data(list).build();
    }

    @ApiOperation(value = "新增渠道请求参数列表", notes = "新增渠道返回参数格式：\n {\"assertValue\":\"string\",\"fieldType\":\"string\",\"parseScript\":\"string\",\"status\":0}")
    @PostMapping("/add")
    public ResponseVo save(@RequestBody @Validated(CreateGroup.class) ResponseParamVO responseParamVO) {
        ResponseParam responseParam;
        if (withDrawRequestService.existsWithPrimaryKey(responseParamVO.getRequestId())) {
            responseParam = new ResponseParam();
            if (responseParamVO.getStatus() != null) {
                if (StateEnum.isValidcode(responseParamVO.getStatus())) {
                    responseParam.setStatus(responseParamVO.getStatus());
                } else {
                    return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
                }
            }
            ResponseParam query = new ResponseParam();
            query.setRequestId(responseParamVO.getRequestId());
            query.setFieldType(responseParamVO.getFieldType());
            ResponseParam info = responseParamService.queryOne(query);
            if (info != null) {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("filedType对应记录已经存在").build();
            }
            responseParam.setRequestId(responseParamVO.getRequestId());
            responseParam.setFieldType(responseParamVO.getFieldType());
            responseParam.setParseScript(responseParamVO.getParseScript());
            responseParam.setAssertValue(responseParamVO.getAssertValue());
            responseParam.setModifyUserId(getUserId());
            responseParam.setUpdateTime(new Date());
            responseParamService.save(responseParam);
        } else {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("渠道请求信息不存在").build();
        }
        return ResponseVo.builder().data(responseParam).build();
    }

    @ApiOperation(value = "更新渠道返回参数信息")
    @PostMapping("/update")
    public ResponseVo update(@RequestBody @Validated(UpdateGroup.class) ResponseParamVO responseParamVO) {
        ResponseParam responseParam = responseParamService.queryById(responseParamVO.getId());
        if (responseParam == null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
        }
        if (responseParamVO.getStatus() != null) {
            if (StateEnum.isValidcode(responseParamVO.getStatus())) {
                responseParam.setStatus(responseParamVO.getStatus());
            } else {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();
            }
        }
        if (!responseParam.getFieldType().equals(responseParamVO.getFieldType())) {
            ResponseParam query = new ResponseParam();
            query.setRequestId(responseParamVO.getRequestId());
            query.setFieldType(responseParamVO.getFieldType());
            ResponseParam info = responseParamService.queryOne(query);
            if (info != null) {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("filedType对应记录已经存在").build();
            }
        }

        responseParam.setFieldType(responseParamVO.getFieldType());
        responseParam.setParseScript(responseParamVO.getParseScript());
        responseParam.setAssertValue(responseParamVO.getAssertValue());
        responseParam.setModifyUserId(getUserId());
        responseParam.setUpdateTime(new Date());
        responseParamService.update(responseParam);
        return ResponseVo.builder().build();
    }

    @ApiOperation(value = "删除渠道返回参数信息")
    @DeleteMapping("/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        boolean extis = responseParamService.existsWithPrimaryKey(id);
        if (extis) {
            responseParamService.deleteById(id);
            return ResponseVo.builder().build();
        }
        return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).build();

    }

    @ApiModel("渠道返回参数VO")
    @Data
    public static class ResponseParamVO {
        @ApiModelProperty(value = "主键id")
        @NotNull(message = "主键ID不能为空", groups = UpdateGroup.class)
        private Long id;
        /**
         * 请求id
         */
        @ApiModelProperty(value = "请求id")
        @NotNull(message = "渠道请求id不能为空", groups = CreateGroup.class)
        private Long requestId;

        /**
         * 参数类型 枚举 mchid orderid等
         */
        @ApiModelProperty(value = "参数类型")
        @NotNull(message = "参数类型不能为空", groups = {CreateGroup.class, UpdateGroup.class})
        private String fieldType;

        /**
         * 解析脚本
         */
        @ApiModelProperty(value = "解析脚本")
        @NotNull(message = "解析脚本不能为空", groups = {CreateGroup.class, UpdateGroup.class})
        private String parseScript;

        /**
         * 成功返回值
         */
        @ApiModelProperty(value = " 成功返回值")
        @NotNull(message = "成功返回值不能为空", groups = {CreateGroup.class, UpdateGroup.class})
        private String assertValue;

        /**
         * 状态 0 启用 1 停用
         */
        @ApiModelProperty(value = " 状态")
        private Integer status;

    }
}
