package com.f.mvc.service;

import com.f.mvc.dao.PaymentCallbackParamDao;
import com.f.mvc.entity.PaymentCallbackParam;
import com.f.mvc.service.PaymentCallbackParamService;
import com.f.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/22.
 */
@Service
@Transactional
public class PaymentCallbackParamServiceImpl extends BaseService<PaymentCallbackParam> implements PaymentCallbackParamService {
    @Resource
    private PaymentCallbackParamDao paymentcallbackparamDao;

}
