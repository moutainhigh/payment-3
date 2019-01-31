package com.f.mvc.dao;

import com.f.mvc.mapper.ResponseParamMapper;
import com.f.mvc.entity.ResponseParam;
import com.f.mvc.dao.ResponseParamDao;
import com.f.core.BaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/17.
 */
@Service
@Transactional
public class ResponseParamDaoImpl extends BaseDao<ResponseParam> implements ResponseParamDao {
    @Resource
    private ResponseParamMapper responseparamMapper;

}
