package com.f.mvc.dao;

import com.f.mvc.mapper.PaymentCallbackParamMapper;
import com.f.mvc.entity.PaymentCallbackParam;
import com.f.mvc.dao.PaymentCallbackParamDao;
import com.f.core.BaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/22.
 */
@Service
@Transactional
public class PaymentCallbackParamDaoImpl extends BaseDao<PaymentCallbackParam> implements PaymentCallbackParamDao {
    @Resource
    private PaymentCallbackParamMapper paymentcallbackparamMapper;

}
