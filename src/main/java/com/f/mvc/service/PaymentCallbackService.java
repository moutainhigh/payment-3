package com.f.mvc.service;

import com.f.core.IService;
import com.f.mvc.entity.PaymentCallback;


/**
 * Created by CodeGenerator on 2019/01/22.
 */
public interface PaymentCallbackService extends IService<PaymentCallback> {
    public PaymentCallback findByChannleId(final String channleId, final int type, final int processType);

}
