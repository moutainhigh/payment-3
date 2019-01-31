package com.f.mvc.dao;

import com.f.mvc.mapper.OrderMapper;
import com.f.mvc.entity.Order;
import com.f.mvc.dao.OrderDao;
import com.f.core.BaseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/25.
 */
@Service
@Transactional
public class OrderDaoImpl extends BaseDao<Order> implements OrderDao {
    @Resource
    private OrderMapper orderMapper;

}
