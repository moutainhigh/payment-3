package com.f.mvc.dao;

import com.f.core.BaseDao;
import com.f.mvc.entity.AccountBank;
import com.f.mvc.mapper.AccountBankMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/15.
 */
@Service
@Transactional
public class AccountBankDaoImpl extends BaseDao<AccountBank> implements AccountBankDao {
    @Resource
    private AccountBankMapper accountbankMapper;
}
