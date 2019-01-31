package com.f.mvc.dao;

import com.f.mvc.mapper.AccountInfoMapper;
import com.f.mvc.entity.AccountInfo;
import com.f.mvc.dao.AccountInfoDao;
import com.f.core.BaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/15.
 */
@Service
@Transactional
public class AccountInfoDaoImpl extends BaseDao<AccountInfo> implements AccountInfoDao {
    @Resource
    private AccountInfoMapper accountinfoMapper;
}
