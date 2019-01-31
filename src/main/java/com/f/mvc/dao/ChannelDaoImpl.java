package com.f.mvc.dao;

import com.f.mvc.mapper.ChannelMapper;
import com.f.mvc.entity.Channel;
import com.f.mvc.dao.ChannelDao;
import com.f.core.BaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/09.
 */
@Service
@Transactional
public class ChannelDaoImpl extends BaseDao<Channel> implements ChannelDao {
    @Resource
    private ChannelMapper channelMapper;

}
