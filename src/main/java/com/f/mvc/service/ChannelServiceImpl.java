package com.f.mvc.service;

import com.f.mvc.dao.ChannelDao;
import com.f.mvc.entity.Channel;
import com.f.mvc.service.ChannelService;
import com.f.core.BaseService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/09.
 */
@Service
@Transactional
public class ChannelServiceImpl extends BaseService<Channel> implements ChannelService {
    @Resource
    private ChannelDao channelDao;
}
