package com.f.mvc.dao;

import com.f.mvc.mapper.TransactionInfoMapper;
import com.f.mvc.entity.TransactionInfo;
import com.f.mvc.dao.TransactionInfoDao;
import com.f.core.BaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/09.
 */
@Service
@Transactional
public class TransactionInfoDaoImpl extends BaseDao<TransactionInfo> implements TransactionInfoDao {
    @Resource
    private TransactionInfoMapper transactioninfoMapper;

}
