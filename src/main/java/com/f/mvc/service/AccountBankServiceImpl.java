package com.f.mvc.service;

import com.f.mvc.dao.AccountBankDao;
import com.f.core.BaseService;
import com.f.mvc.entity.AccountBank;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/15.
 */
@Service
@Transactional
public class AccountBankServiceImpl extends BaseService<AccountBank> implements AccountBankService {
    @Resource
    private AccountBankDao accountbankDao;

}
