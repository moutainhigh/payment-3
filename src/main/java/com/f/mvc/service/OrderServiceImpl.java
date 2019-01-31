package com.f.mvc.service;

import com.f.mvc.dao.OrderDao;
import com.f.mvc.entity.Order;
import com.f.mvc.service.OrderService;
import com.f.core.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/25.
 */
@Service
@Transactional
public class OrderServiceImpl extends BaseService<Order> implements OrderService {
    @Resource
    private OrderDao orderDao;

}
