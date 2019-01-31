package com.f.mvc.dao;

import com.f.mvc.mapper.SignSettingMapper;
import com.f.mvc.entity.SignSetting;
import com.f.mvc.dao.SignSettingDao;
import com.f.core.BaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/25.
 */
@Service
@Transactional
public class SignSettingDaoImpl extends BaseDao<SignSetting> implements SignSettingDao {
    @Resource
    private SignSettingMapper signsettingMapper;

}
