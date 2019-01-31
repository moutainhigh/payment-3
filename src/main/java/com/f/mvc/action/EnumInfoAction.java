package com.f.mvc.action;

import com.f.enums.*;
import com.f.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rebysfu@gmail.com
 * @description：枚举值参数查询
 * @create 2019-01-17 下午12:18
 **/
@RestController
@RequestMapping("/enuminfo")
@Api(value = "系统需要的枚举参数查询", description = "系统需要的枚举参数查询")
public class EnumInfoAction {

    /**
     * 查询请求等类型
     *
     * @return
     */
    @ApiOperation(value = "查询请求类型接口", notes = "查询请求类型接口")
    @RequestMapping(value = "/queryRequestType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo query() {
        return ResponseVo.builder().data(PayInterfaceType.getPayInterfaceTypeMap()).build();
    }

    /**
     * 查询提现相关状态
     *
     * @return
     */
    @ApiOperation(value = "提现相关状态", notes = "提现相关状态")
    @RequestMapping(value = "/queryPayStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo queryPayStatus() {
        return ResponseVo.builder().data(PayStatus.getPayStatusMap()).build();
    }

    /**
     * 查询加密方式接口
     *
     * @return
     */
    @ApiOperation(value = "查询加密方式接口", notes = "查询加密方式接口")
    @RequestMapping(value = "/querySignType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo querySignType() {
        return ResponseVo.builder().data(SignType.getSignTypeMap()).build();
    }

    /**
     * 查询金额单位
     *
     * @return
     */
    @ApiOperation(value = "查询金额单位", notes = "查询金额单位")
    @RequestMapping(value = "/queryAmtUnit", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo queryAmtUnit() {
        return ResponseVo.builder().data(AmtUnit.getAmtUnitMap()).build();
    }

    /**
     * 查询支付类型
     *
     * @return
     */
    @ApiOperation(value = "查询支付类型", notes = "查询支付类型")
    @RequestMapping(value = "/queryPayTypeCode", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo queryPayTypeCode() {
        return ResponseVo.builder().data(PayTypeCode.getPayTypeCodeMap()).build();
    }

    /**
     * 查询请求response解析方式
     *
     * @return
     */
    @ApiOperation(value = "查询请求response解析方式", notes = "查询请求response解析方式")
    @RequestMapping(value = "/queryResponseParseType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo queryResponseParseType() {
        return ResponseVo.builder().data(ResponseParseType.values()).build();
    }

    /**
     * 查询参数类型
     *
     * @return
     */
    @ApiOperation(value = "查询参数类型", notes = "查询参数类型")
    @RequestMapping(value = "/queryFieldType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo queryFieldType() {
        return ResponseVo.builder().data(FieldType.getName2Desc()).build();
    }

    /**
     * 查询所有银行类型列表以及详情
     *
     * @return
     */
    @ApiOperation(value = "/queryBankType", notes = "查询所有银行类型列表")
    @RequestMapping(value = "/queryBankType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo queryBankType() {
        return ResponseVo.builder().data(BankTypeEnum.getBankTypeList()).build();
    }

    /**
     * 查询所有银行类型列表以及详情
     *
     * @return
     */
    @ApiOperation(value = "/queryRequestMethod", notes = "查询所有请求方式")
    @RequestMapping(value = "/queryRequestMethod", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo queryRequestMethod() {
        return ResponseVo.builder().data(com.f.enums.RequestMethod.values()).build();
    }

}
