package com.f.mvc.service;

import com.f.mvc.dao.ChannelMerchantDao;
import com.f.mvc.entity.ChannelMerchant;
import com.f.mvc.service.ChannelMerchantService;
import com.f.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/25.
 */
@Service
@Transactional
public class ChannelMerchantServiceImpl extends BaseService<ChannelMerchant> implements ChannelMerchantService {
    @Resource
    private ChannelMerchantDao channelmerchantDao;

}
