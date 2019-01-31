package com.f.mvc.service;

import com.f.core.BaseService;
import com.f.mvc.dao.PaymentCallbackDao;
import com.f.mvc.entity.PaymentCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/22.
 */
@Service
@Transactional
public class PaymentCallbackServiceImpl extends BaseService<PaymentCallback> implements PaymentCallbackService {
    @Resource
    private PaymentCallbackDao paymentcallbackDao;

    @Override
    public PaymentCallback findByChannleId(String channleId, int type, int processType) {
        PaymentCallback callback = new PaymentCallback();
        callback.setChannelId(channleId);
        callback.setType(type);
        callback.setProcessType(processType);
        PaymentCallback paymentCallback = this.queryOne(callback);
        return paymentCallback;
    }
}
