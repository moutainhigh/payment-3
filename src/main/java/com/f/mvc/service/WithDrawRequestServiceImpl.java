package com.f.mvc.service;

import com.f.mvc.dao.WithDrawRequestDao;
import com.f.mvc.entity.WithDrawRequest;
import com.f.mvc.service.WithDrawRequestService;
import com.f.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/18.
 */
@Service
@Transactional
public class WithDrawRequestServiceImpl extends BaseService<WithDrawRequest> implements WithDrawRequestService {
    @Resource
    private WithDrawRequestDao withdrawrequestDao;

}
