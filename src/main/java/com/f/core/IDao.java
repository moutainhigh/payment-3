package com.f.core;

import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author rebysfu@gmail.com
 * @description：
 * @create 2019-01-06 上午6:22
 **/
public interface IDao<T> {
    /**
     * 根据id查询
     */
    public T queryById(Long id);

    /**
     * 根据条件查询一条数据
     */
    public T queryOne(T example);

    /**
     * 查询所有数据
     */
    public List<T> queryAll();

    /**
     * 根据条件查询数据列表
     */
    public List<T> queryListByWhere(T example);

    /**
     * 分页查询数据列表
     */
    public PageInfo<T> queryPageListByWhere(T example);

    /**
     * 根据Example 查询
     */
    public List<T> queryListByExample(Object example);

    /**
     * 分页查询数据列表
     */
    public PageInfo<T> queryPageListByExample(Object example);

    /**
     * 新增数据，注意设置数据的创建和更新时间
     * 返回成功的条数
     */
    public Integer save(T entity);

    /**
     * 更新数据，设置数据的更新时间
     * 返回成功的条数
     */
    public Integer update(T entity);

    /**
     * 更新数据，设置数据的更新时间（更新部分数据）
     * 返回成功的条数
     */
    public Integer updateSelective(T entity);

    /**
     * 根据id删除数据
     */
    public Integer deleteById(final Long id);

    /**
     * 批量删除数据
     */
    public Integer deleteByIds(final Example example);

    /**
     * 根据条件删除数据
     */
    public Integer deleteByWhere(T example);

    /**
     * 批量插入
     **/
    public Integer insertList(List<T> list);

    /**
     * 判断数据是否存在
     **/
    public boolean existsWithPrimaryKey(Long id);
}

