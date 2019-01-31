package com.f.mvc.dao;

import com.f.mvc.mapper.BankMapper;
import com.f.mvc.entity.Bank;
import com.f.mvc.dao.BankDao;
import com.f.core.BaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/15.
 */
@Service
@Transactional
public class BankDaoImpl extends BaseDao<Bank> implements BankDao {
    @Resource
    private BankMapper bankMapper;
}
