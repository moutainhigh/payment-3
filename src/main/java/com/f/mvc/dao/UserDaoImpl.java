package com.f.mvc.dao;

import com.f.datasource.annotations.DataSource;
import com.f.enums.DataSourceKey;
import com.f.mvc.entity.User;
import com.f.mvc.mapper.UserMapper;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author rebysfu@gmail.com
 * @description：
 * @create 2018-09-24 下午2:18
 **/
@DataSource(DataSourceKey.WITHDRAW)
@Repository
public class UserDaoImpl implements UserDao {
    @Resource
    private UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public User findUserByAccount(String account) {
        return userMapper.findUserByAccount(account);
    }

    @Override
    public User findUserById(Long id) {
        return userMapper.findUserById(id);
    }

    @Override
    public int modifyUser(User user) {
        return userMapper.modifyUser(user);
    }

    @Override
    public List<User> findUserByParam(String keyword, Page<User> page) {
        return userMapper.findUserByParam(keyword, page);
    }

    @Override
    public int removeUser(User user) {
        return userMapper.deleteUser(user);
    }

    @Override
    public List<User> findUserLikeParam(String param) {
        return userMapper.findUserLikeParam(param);
    }
}
