package com.f.mvc.service;

import com.f.mvc.dao.SignSettingDao;
import com.f.mvc.entity.SignSetting;
import com.f.mvc.service.SignSettingService;
import com.f.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/25.
 */
@Service
@Transactional
public class SignSettingServiceImpl extends BaseService<SignSetting> implements SignSettingService {
    @Resource
    private SignSettingDao signsettingDao;

}
