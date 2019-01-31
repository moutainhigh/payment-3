package com.f.request.task;
/*
 *@ClassName QuerryTask
 *@Author 建国
 *@Date 1/16/19 4:54 PM
 */

import com.f.enums.RequestType;
import com.f.enums.ResponseParseType;
import com.f.mvc.entity.TransactionInfo;
import com.f.mvc.service.TransactionInfoService;
import com.f.transport.ParseResult;
import com.f.vo.RequestContextVO;
import com.f.vo.RequestParamVo;
import com.f.vo.ResponseParamVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class QuerryBalanceTask extends RequestTask{
    @Autowired
    private TransactionInfoService transactionInfoService;

    public  QuerryBalanceTask(List<RequestParamVo> requestParamVos, List<ResponseParamVo> responseParams, RequestContextVO requestContextVO, ResponseParseType responseParseType){
        super(requestParamVos,responseParams,requestContextVO,responseParseType);
    }


    @Override
    protected void dealTask(ParseResult result) throws Exception {
        //TODO  目前是测试代码只做测试
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setChannelId("testChannel");
        transactionInfo.setChannleName("太天真了，只是个测试");
        transactionInfo.setFee(9527);
        transactionInfo.setStatus(0);
        transactionInfo.setTranFee(20);
        transactionInfo.setTotalAmt(9528);
        transactionInfoService.save(transactionInfo);
        //TODO 具体写库任务 在子类中实现deal方法去写
        deal(result);
    }

    protected  abstract void deal(ParseResult result);

    @Override
    public RequestType getType() {
        return RequestType.CHECK_BALANCE;
    }
}
