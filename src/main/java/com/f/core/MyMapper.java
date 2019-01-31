package com.f.core;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author rebysfu@gmail.com
 * @description：
 * @create 2019-01-06 上午5:34
 **/
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
