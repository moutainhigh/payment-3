package com.f.mvc.service;

import com.f.mvc.dao.AccountInfoDao;
import com.f.mvc.entity.AccountInfo;
import com.f.mvc.service.AccountInfoService;
import com.f.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/15.
 */
@Service
@Transactional
public class AccountInfoServiceImpl extends BaseService<AccountInfo> implements AccountInfoService {
    @Resource
    private AccountInfoDao accountinfoDao;

}
