package com.f.mvc.service.notify;

import com.f.enums.FieldType;
import com.f.enums.PayStatus;
import com.f.mvc.entity.*;
import com.f.mvc.service.*;
import com.f.transport.CallbackContext;
import com.f.transport.ParseResult;
import com.f.transport.TransportService;
import com.f.utils.JsonBuilder;
import com.f.utils.StringUtils;
import com.f.utils.Tools;
import com.f.vo.CallbackParamVo;
import com.f.vo.SignSettingVo;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author rebysfu@gmail.com
 * @description：异步提现回调接口
 * @create 2019-01-28 下午12:06
 **/
@Log4j2
@Service(value = "withDrawNotifyService")
public class WithDrawNotifyServiceImpl implements NotifyService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentCallbackService paymentCallbackService;
    @Autowired
    private PaymentCallbackParamService paymentCallbackParamService;
    @Autowired
    private TransportService transportService;

    @Autowired
    private ChannelMerchantService channelMerchantService;
    @Autowired
    private SignSettingService signSettingService;

    //如果这里缓存了，需要一个清除缓存的接口
    //private Map<String, PaymentCallback> callbackMap = new ConcurrentHashMap<>();

    @Override
    public Map<String, Object> loadPayInfo(String channleId, String merchantId, HttpServletRequest request) throws IOException {
        Map<String, Object> requestMap = new TreeMap<>();
        try {
            Enumeration<String> names = request.getParameterNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                String value = request.getParameter(name);
                if (StringUtils.isNotEmpty(value)) {
                    requestMap.put(name, value);
                }
            }
        } catch (Exception e) {
            try {
                String json = Tools.getRequestBody(request);
                requestMap = JsonBuilder.desJson(json);
            } catch (Exception e1) {
                throw new IOException("渠道编码" + channleId + ",商户号" + merchantId + "提现回调信息解析异常");
            }
        }
        log.debug("渠道编码" + channleId + ",商户号" + merchantId + "提现回调信息:" + requestMap);
        return requestMap;
    }

    //type 回调类型枚举 0 代付提现 1 下单
    //processType 处理类型 0实现类 1通用配置
    @Override
    public String getSuccessfulResponseData(String channleId) {
        // PaymentCallback paymentCallback =callbackMap.computeIfAbsent(channleId,k->paymentCallbackService.findByChannleId(channleId));
        PaymentCallback paymentCallback = paymentCallbackService.findByChannleId(channleId, 0, 1);
        return paymentCallback.getSuccessResponse();
    }

    @Override
    public String getFailedResponseData(String channleId) {
        PaymentCallback paymentCallback = paymentCallbackService.findByChannleId(channleId, 0, 1);
        return paymentCallback.getErrorResponse();
    }

    @Override
    public boolean isWithDrawSuccess(String channleId, String merchantId, Map<String, Object> data) {
        PaymentCallback paymentCallback = getCallBallBack(channleId);
        if (paymentCallback == null) {
            return false;
        }
        List<PaymentCallbackParam> callbackParams = getCallBackParams(channleId);
        if (CollectionUtils.isEmpty(callbackParams)) {
            return false;
        }
        AtomicReference<String> filedCodeKey = new AtomicReference<>("");
        callbackParams.stream().forEach(item -> {
            if (item.getFieldType().equals(FieldType.CODE)) {
                filedCodeKey.set(item.getFieldKey());
            }
        });
        String code = data.get(filedCodeKey.get()).toString();
        boolean paystatu = paymentCallback.getSuccessResponse().equals(code);
        if (!paystatu) {
            Order order = bulidOrder(channleId, merchantId, data);
            order.setStatus(Integer.parseInt(PayStatus.PAY_FAIL.getValue()));
            order.setNote("");
            orderService.updateSelective(order);
        }


        return paystatu;
    }

    private Order bulidOrder(String channleId, String merchantId, Map<String, Object> data) {
        Order ordervo = new Order();
        List<PaymentCallbackParam> callbackParams = getCallBackParams(channleId);
        callbackParams.stream().forEach(item -> {
            if (item.getFieldType().equals(FieldType.ORDER_NO)) {
                ordervo.setOrderNo(data.get(item.getFieldKey()).toString());
            }
            if (item.getFieldType().equals(FieldType.THIRD_ORDERNO)) {
                ordervo.setOutTradeNo(data.get(item.getFieldKey()).toString());
            }
        });
        Order query = new Order();
        query.setOrderNo(ordervo.getOrderNo());
        Order order = orderService.queryOne(query);
        if (order == null) {
            throw new RuntimeException("渠道编码：" + channleId + "商户号：" + merchantId + "订单号：" + order.getOrderNo() + "系统不存在");
        }
        return order;

    }

    @Override
    public boolean verifyData(String channleId, String merchantId, Map<String, Object> postData) {
        List<PaymentCallbackParam> callbackParams = getCallBackParams(channleId);
        if (CollectionUtils.isEmpty(callbackParams)) {
            return false;
        }
        List<CallbackParamVo> callbackParamVos = new ArrayList<>();
        callbackParams.stream().forEach(item -> {
            CallbackParamVo callbackParamVo = new CallbackParamVo();
            BeanUtils.copyProperties(item, callbackParamVo);
            callbackParamVos.add(callbackParamVo);
        });
        try {
            ParseResult result = transportService.callback(new CallbackContext() {
                @Override
                public String getKey() {
                    return getMerchantkey(channleId, merchantId);
                }

                @Override
                public SignSettingVo getSignSetting() {
                    return getSignSettingVo(channleId);
                }
            }, callbackParamVos, postData);
            return result.isSuccess();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }


    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void withDrawSuccess(String channleId, String merchantId, Map<String, Object> params) {
        Order order = bulidOrder(channleId, merchantId, params);
        order.setStatus(Integer.valueOf(PayStatus.PAY_SUCCESS.getValue()));
        order.setCompleteTime(new Date());
        orderService.updateSelective(order);
    }

    private List<PaymentCallbackParam> getCallBackParams(String channleId) {
        PaymentCallback paymentCallback = getCallBallBack(channleId);
        if (Optional.ofNullable(paymentCallback).isPresent()) {
            return Lists.newArrayList();
        }
        PaymentCallbackParam callbackParam = new PaymentCallbackParam();
        callbackParam.setCallbackId(paymentCallback.getId());
        List<PaymentCallbackParam> callbackParams = paymentCallbackParamService.queryListByWhere(callbackParam);
        if (CollectionUtils.isEmpty(callbackParams)) {
            log.error(channleId + "提现回调请求参数未配置");
            return Lists.newArrayList();
        }
        return callbackParams;
    }

    private PaymentCallback getCallBallBack(String channleId) {
        PaymentCallback paymentCallback = paymentCallbackService.findByChannleId(channleId, 0, 1);
        if (Optional.ofNullable(paymentCallback).isPresent()) {
            log.error(channleId + "提现回调请求未配置");
            return null;
        }
        return paymentCallback;
    }

    private String getMerchantkey(String channleId, String merchantId) {
        ChannelMerchant query = new ChannelMerchant();
        query.setMerchantId(merchantId);
        query.setChannelId(channleId);
        ChannelMerchant merchant = channelMerchantService.queryOne(query);
        if (merchant == null) {
            throw new RuntimeException(channleId + "获取商户key异常");
        }
        return merchant.getAppkey();
    }

    private SignSettingVo getSignSettingVo(String channleId) {
        PaymentCallback paymentCallback = getCallBallBack(channleId);
        SignSettingVo signSettingVo = new SignSettingVo();
        SignSetting query = new SignSetting();
        query.setForeignKey(paymentCallback.getId().toString());
        SignSetting signSetting = signSettingService.queryOne(query);
        if (signSetting == null) {
            throw new RuntimeException(channleId + "获取加密配置异常");
        }
        BeanUtils.copyProperties(signSetting, signSettingVo);
        return signSettingVo;
    }
}
