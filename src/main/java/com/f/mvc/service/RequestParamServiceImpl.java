package com.f.mvc.service;

import com.f.mvc.dao.RequestParamDao;
import com.f.mvc.entity.RequestParam;
import com.f.mvc.service.RequestParamService;
import com.f.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/17.
 */
@Service
@Transactional
public class RequestParamServiceImpl extends BaseService<RequestParam> implements RequestParamService {
    @Resource
    private RequestParamDao requestparamDao;

}
