package com.f.mvc.service;

import com.f.mvc.dao.TransactionInfoDao;
import com.f.mvc.entity.TransactionInfo;
import com.f.mvc.service.TransactionInfoService;
import com.f.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/09.
 */
@Service
@Transactional
public class TransactionInfoServiceImpl extends BaseService<TransactionInfo> implements TransactionInfoService {
    @Resource
    private TransactionInfoDao transactioninfoDao;

}
