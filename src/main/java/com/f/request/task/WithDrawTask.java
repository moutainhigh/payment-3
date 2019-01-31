package com.f.request.task;
/*
 *@ClassName WithDrawTask
 *@Author 建国
 *@Date 1/16/19 4:53 PM
 */

import com.f.enums.PayStatus;
import com.f.enums.RequestType;
import com.f.enums.ResponseParseType;
import com.f.mvc.entity.AccountBank;
import com.f.mvc.entity.Order;
import com.f.mvc.service.AccountBankService;
import com.f.mvc.service.OrderService;
import com.f.mvc.service.WithDrawRequestService;
import com.f.transport.ParseResult;
import com.f.vo.RequestContextVO;
import com.f.vo.RequestParamVo;
import com.f.vo.ResponseParamVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
@Log4j2
public class WithDrawTask extends RequestTask{

    @Autowired
    private  WithDrawRequestService withDrawRequestService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AccountBankService accountBankService;

    String orderNo;

    public WithDrawTask(){

    }

    public  WithDrawTask(List<RequestParamVo> requestParamVos,List<ResponseParamVo> responseParams, RequestContextVO requestContextVO,ResponseParseType responseParseType,String orderNo){
        super(requestParamVos,responseParams,requestContextVO,responseParseType);
        this.orderNo = orderNo;
    }

    @Override
    protected void dealTask(ParseResult result) throws Exception{
        Order querryOrder = new Order();
        querryOrder.setOrderNo(this.orderNo);
        Order order = orderService.queryOne(querryOrder);
        if(result.isSuccess())
            order.setStatus(Integer.parseInt(PayStatus.PAY_SUCCESS.getValue()));
        else
            order.setStatus(Integer.parseInt(PayStatus.PAY_FAIL.getValue()));
        orderService.update(order);
        AccountBank accountBankQuerry = new AccountBank();
        accountBankQuerry.setAcountid(order.getMerhtAccount());
        accountBankQuerry.setCardNo(order.getCardNo());
        accountBankService.deleteByWhere(accountBankQuerry);
    }

    public  static void main(String[]args){
        //WithDrawTask task = new WithDrawTask(null,null,null,"",null);
        //HttpTaskManager.addTask(task);
    }
    @Override
    public RequestType getType() {
        return RequestType.DAIFU;
    }
}
