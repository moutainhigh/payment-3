package com.f.request;/*
 *@ClassName RequestService
 *@Author 建国
 *@Date 1/22/19 4:09 PM
 */

import com.f.enums.FieldType;
import com.f.enums.RequestType;
import com.f.enums.ResponseParseType;
import com.f.exception.LogicException;
import com.f.mvc.entity.*;
import com.f.mvc.service.ChannelMerchantService;
import com.f.mvc.service.RequestParamService;
import com.f.mvc.service.ResponseParamService;
import com.f.mvc.service.WithDrawRequestService;
import com.f.request.task.WithDrawTask;
import com.f.thread.HttpTaskManager;
import com.f.vo.RequestContextVO;
import com.f.vo.RequestParamVo;
import com.f.vo.ResponseParamVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class RequestService {
    @Autowired
    private ChannelMerchantService channelMerchantService;
    @Autowired
    private WithDrawRequestService withDrawRequestService;
    @Autowired
    private RequestParamService requestParamService;
    @Autowired
    private ResponseParamService responseParamService;

    public void submitWithDrawRequestTask(Order order, Map<FieldType, Object> data,String merchantId) throws LogicException {
        ChannelMerchant querryChannelMerchant = new ChannelMerchant();
        querryChannelMerchant.setMerchantId(merchantId);
        querryChannelMerchant.setChannelId(order.getChannelId());
        querryChannelMerchant = channelMerchantService.queryOne(querryChannelMerchant);
        if (querryChannelMerchant == null)
            throw new LogicException("渠道id或者商户id错误", 0);
        String key = querryChannelMerchant.getAppkey();
        WithDrawRequest qurryRequest = new WithDrawRequest();
        qurryRequest.setChannelId(order.getChannelId());
        qurryRequest.setType(RequestType.DAIFU.ordinal());
        qurryRequest = withDrawRequestService.queryOne(qurryRequest);
        if (qurryRequest == null)
            throw new LogicException("渠道id或者请求类型错误", 0);
        RequestParam requestParam = new RequestParam();
        requestParam.setRequestId(qurryRequest.getId());
        List<RequestParam> requestParams = requestParamService.queryListByWhere(requestParam);
        if (requestParam == null || requestParams.isEmpty())
            throw new LogicException("请检查渠道请求参数配置，配置为空", 0);

        ResponseParam responseParamQuerry = new ResponseParam();
        responseParamQuerry.setRequestId(qurryRequest.getId());
        List<ResponseParam> responseParams = responseParamService.queryListByWhere(responseParamQuerry);
        if (responseParams == null || responseParams.isEmpty())
            throw new LogicException("请检查渠道返回参数配置，配置为空", 0);

        List<RequestParamVo> requestParamVos = new ArrayList<>();
        List<ResponseParamVo> responseParamVos = new ArrayList<>();
        requestParams.forEach(param -> requestParamVos.add(param.transToVO()));
        responseParams.forEach(param -> responseParamVos.add(param.transToVO()));

        RequestContextVO requestContextVO = new RequestContextVO();
        requestContextVO.setKey(key);
        requestContextVO.setMoney(order.getAmount());
        requestContextVO.setData(data);
        HttpTaskManager.addTask(new WithDrawTask(requestParamVos, responseParamVos,
                requestContextVO,
                ResponseParseType.valueOf(qurryRequest.getParseType()), order.getOrderNo()));
    }

    public void submitQuerryBalanceTask(WithDrawRequest request) {
        try {
            ChannelMerchant querryChannelMerchant = new ChannelMerchant();
            querryChannelMerchant.setChannelId(request.getChannelId());
            List<ChannelMerchant> channelMerchants = channelMerchantService.queryListByWhere(querryChannelMerchant);
            if (querryChannelMerchant == null)
                throw new LogicException("渠道id或者商户id错误", 0);
            for (ChannelMerchant channelMerchant : channelMerchants) {
                RequestParam requestParam = new RequestParam();
                String key = querryChannelMerchant.getAppkey();
                requestParam.setRequestId(request.getId());
                List<RequestParam> requestParams = requestParamService.queryListByWhere(requestParam);
                if (requestParam == null || requestParams.isEmpty())
                    throw new LogicException("请检查渠道请求参数配置，配置为空", 0);

                ResponseParam responseParamQuerry = new ResponseParam();
                responseParamQuerry.setRequestId(request.getId());
                List<ResponseParam> responseParams = responseParamService.queryListByWhere(responseParamQuerry);
                if (responseParams == null || responseParams.isEmpty())
                    throw new LogicException("请检查渠道返回参数配置，配置为空", 0);

                List<RequestParamVo> requestParamVos = new ArrayList<>();
                List<ResponseParamVo> responseParamVos = new ArrayList<>();
                requestParams.forEach(param -> requestParamVos.add(param.transToVO()));
                responseParams.forEach(param -> responseParamVos.add(param.transToVO()));


                RequestContextVO requestContextVO = new RequestContextVO();
                requestContextVO.setKey(key);
                //requestContextVO.setMoney(order.getAmout());
                //requestContextVO.setData(data);
            }
        } catch (LogicException e) {
            log.error(e.getMessage());
        } catch (Exception e1) {

        }

    }
}
