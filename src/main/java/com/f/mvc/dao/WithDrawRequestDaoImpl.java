package com.f.mvc.dao;

import com.f.mvc.mapper.WithDrawRequestMapper;
import com.f.mvc.entity.WithDrawRequest;
import com.f.mvc.dao.WithDrawRequestDao;
import com.f.core.BaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/18.
 */
@Service
@Transactional
public class WithDrawRequestDaoImpl extends BaseDao<WithDrawRequest> implements WithDrawRequestDao {
    @Resource
    private WithDrawRequestMapper withdrawrequestMapper;

}
