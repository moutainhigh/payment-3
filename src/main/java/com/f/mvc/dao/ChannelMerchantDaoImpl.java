package com.f.mvc.dao;

import com.f.mvc.mapper.ChannelMerchantMapper;
import com.f.mvc.entity.ChannelMerchant;
import com.f.mvc.dao.ChannelMerchantDao;
import com.f.core.BaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/25.
 */
@Service
@Transactional
public class ChannelMerchantDaoImpl extends BaseDao<ChannelMerchant> implements ChannelMerchantDao {
    @Resource
    private ChannelMerchantMapper channelmerchantMapper;

}
