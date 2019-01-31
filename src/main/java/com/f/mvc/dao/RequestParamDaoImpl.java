package com.f.mvc.dao;

import com.f.mvc.mapper.RequestParamMapper;
import com.f.mvc.entity.RequestParam;
import com.f.mvc.dao.RequestParamDao;
import com.f.core.BaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/17.
 */
@Service
@Transactional
public class RequestParamDaoImpl extends BaseDao<RequestParam> implements RequestParamDao {
    @Resource
    private RequestParamMapper requestparamMapper;

}
