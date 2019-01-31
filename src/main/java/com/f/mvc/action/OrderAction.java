package com.f.mvc.action;

import com.f.core.PageInfo;
import com.f.mvc.entity.Order;
import com.f.mvc.service.OrderService;
import com.f.vo.Page;
import com.f.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * Created by CodeGenerator on 2019/01/09.
 */
@Api(value = "提现订单查询", description = "提现订单查询")
@RestController
@RequestMapping("/order")
public class OrderAction {
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "提现订单查询")
    @PostMapping("/list")
    public ResponseVo list(@RequestBody QueryOrder queryOrder) {
        Order order = new Order();
        BeanUtils.copyProperties(queryOrder, order);
        PageInfo<Order> pageinfo = orderService.queryPageListByWhere(order, queryOrder.getPageNum(), queryOrder.getPageSize());
        return ResponseVo.builder().data(pageinfo).build();
    }

    @ApiModel(value = "提现订单查询")
    @Data
    static class QueryOrder extends Page {

        /**
         * 渠道编码
         */
        @ApiModelProperty(value = "渠道编码", required = true)
        @NotNull(message = "渠道编码不能为空")
        private String channelId;

        /**
         * 系统订单号
         */
        @ApiModelProperty(value = "订单编号")
        private String orderNo;

        /**
         * 银行卡号
         */
        @ApiModelProperty(value = "银行卡号")
        private Long cardNo;

        /**
         * 状态 0 待提现 1 提现中 2 成功 3 失败
         */
        @ApiModelProperty(value = " 状态  1 提现中 2 成功 3 失败")
        private Integer status;

    }

}
