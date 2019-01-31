package com.f.mvc.service;

import com.f.mvc.dao.ResponseParamDao;
import com.f.mvc.entity.ResponseParam;
import com.f.mvc.service.ResponseParamService;
import com.f.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/17.
 */
@Service
@Transactional
public class ResponseParamServiceImpl extends BaseService<ResponseParam> implements ResponseParamService {
    @Resource
    private ResponseParamDao responseparamDao;

}
