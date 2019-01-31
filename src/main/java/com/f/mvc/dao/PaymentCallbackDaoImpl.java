package com.f.mvc.dao;

import com.f.mvc.mapper.PaymentCallbackMapper;
import com.f.mvc.entity.PaymentCallback;
import com.f.mvc.dao.PaymentCallbackDao;
import com.f.core.BaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/22.
 */
@Service
@Transactional
public class PaymentCallbackDaoImpl extends BaseDao<PaymentCallback> implements PaymentCallbackDao {
    @Resource
    private PaymentCallbackMapper paymentcallbackMapper;

}
