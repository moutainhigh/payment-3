package com.f.mvc.action;

import com.f.base.BaseAction;
import com.f.core.PageInfo;
import com.f.mvc.entity.TransactionInfo;
import com.f.mvc.service.TransactionInfoService;
import com.f.utils.StringUtils;
import com.f.vo.Page;
import com.f.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.Optional;

/**
 * Created by CodeGenerator on 2019/01/09.
 */
@Api(value = "商户余额信息查询")
@RestController
@RequestMapping("/transactioninfo")
public class TransactionInfoAction extends BaseAction {
    @Autowired
    private TransactionInfoService transactionInfoService;

    @ApiOperation(value = "第三方列表", notes = "查询的第三方可以结算的列表信息")
    @PostMapping("/list")
    public ResponseVo list(@RequestBody Page queryParams) {
        Example example = new Example(TransactionInfo.class);
        if (StringUtils.isNotEmpty(queryParams.getKeyword())) {
            example.createCriteria().orEqualTo("channelId", queryParams.getKeyword())
                    .orEqualTo("merchantId", queryParams.getKeyword());
        }
        PageInfo pageInfo = transactionInfoService.queryPageListByExample(example, queryParams.getPageNum(), queryParams.getPageSize());
        return ResponseVo.builder().data(pageInfo).build();
    }

    @ApiOperation(value = "领取/取消", notes = "状态值 0 (未领取)  1 （已领取）")
    @PostMapping("/receive")
    public ResponseVo update(@RequestBody TransactionInfoVo transactionInfoVo) {

        String lock = super.generateMutex(transactionInfoVo.getId().toString());
        synchronized (lock) {
            TransactionInfo transactionInfo = transactionInfoService.queryById(transactionInfoVo.getId());
            if (transactionInfo == null) {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("商户提现信息已刷新,请重新查询").build();
            }
            if (transactionInfo.getStatus().equals(transactionInfoVo.getStatus())) {
                return ResponseVo.builder().message("").build();
            }

            if ("0".equals(transactionInfo.getStatus()) && transactionInfo.getReceiveUserId().equals(getUserId())) {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("你没有权限取消领取，只有领取人才可以取消领取").build();
            }
            //取消领取
            if ("0".equals(transactionInfoVo.getStatus())) {
                transactionInfo.setReceiveTime(null);
                transactionInfo.setReceiveUserId(null);
                transactionInfo.setStatus(0);
            } else {//领取
                transactionInfo.setReceiveTime(new Date());
                transactionInfo.setReceiveUserId(getUserId());
                transactionInfo.setStatus(1);
            }
            transactionInfoService.update(transactionInfo);
            return ResponseVo.builder().data(transactionInfo).build();
        }
    }

    @ApiModel(value = "交易信息")
    @Data
    static class TransactionInfoVo {
        @ApiModelProperty(value = "主键",required = true)
        private Long id;

        /**
         * 领取状态
         */
        @ApiModelProperty(value = "领取状态（状态值 0 (未领取)  1 (已领取））")
        private Integer status;

        /* *//**
         * 领取时间
         *//*
        private Date receiveTime;

        *//**
         * 领取人
         *//*
        private Long receiveUserId;*/
    }


}
