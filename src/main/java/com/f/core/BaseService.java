package com.f.core;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author rebysfu@gmail.com
 * @description：
 * @create 2019-01-05 下午5:55
 **/
public abstract class BaseService<T> implements IService<T> {
    @Autowired
    private BaseDao<T> dao;

    /**
     * 根据id查询
     */
    public T queryById(Long id) {
        return this.dao.queryById(id);
    }

    /**
     * 根据条件查询一条数据
     */
    public T queryOne(T example) {
        return this.dao.queryOne(example);
    }

    /**
     * 查询所有数据
     */
    public List<T> queryAll() {
        return this.dao.queryAll();
    }

    /**
     * 根据条件查询数据列表
     */
    public List<T> queryListByWhere(T example) {
        return this.dao.queryListByWhere(example);
    }

    /**
     * 分页查询数据列表
     *
     * @param example 查询条件
     * @param page    页数
     * @param rows    页面大小
     * @return
     */
    public PageInfo<T> queryPageListByWhere(T example, Integer page, Integer rows) {
        //设置分页参数
        PageHelper.startPage(page, rows);
        com.github.pagehelper.PageInfo<T> pageInfo = this.dao.queryPageListByWhere(example);
        return (PageInfo<T>) PageInfo.builder().currentPageNo(pageInfo.getPageNum()).total(pageInfo.getTotal()).list((List<Object>) pageInfo.getList()).build();
    }

    /**
     * 根据Example 查询
     */
    public List<T> queryListByExample(Object example) {
        return this.dao.queryListByExample(example);
    }

    /**
     * 分页查询数据列表
     */
    public PageInfo<T> queryPageListByExample(Object example, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        com.github.pagehelper.PageInfo<T> pageInfo = this.dao.queryPageListByExample(example);
        return (PageInfo<T>) PageInfo.builder().currentPageNo(pageInfo.getPageNum()).total(pageInfo.getTotal()).list((List<Object>) pageInfo.getList()).build();
    }

    /**
     * 新增数据，注意设置数据的创建和更新时间
     * 返回成功的条数
     */
    public Integer save(T entity) {
        return this.dao.save(entity);

    }

    /**
     * 更新数据，设置数据的更新时间
     * 返回成功的条数
     */
    public Integer update(T entity) {
        return this.dao.update(entity);
    }

    /**
     * 更新数据，设置数据的更新时间（更新部分数据）
     * 返回成功的条数
     */
    public Integer updateSelective(T entity) {
        return this.dao.updateSelective(entity);
    }

    /**
     * 根据id删除数据
     */
    public Integer deleteById(Long id) {
        return this.dao.deleteById(id);
    }

    /**
     * 批量删除数据
     *
     * @param clazz
     * @param property
     * @param list
     * @return
     */
    public Integer deleteByIds(Class<T> clazz, String property, List<Object> list) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, list);
        return this.dao.deleteByIds(example);
    }

    /**
     * 根据条件删除数据
     */
    public Integer deleteByWhere(T example) {
        return this.dao.deleteByWhere(example);
    }

    /**
     * 批量插入
     **/
    public Integer insertList(List<T> list) {
        return this.dao.insertList(list);
    }

    /**
     * 判断数据是否存在
     **/
    public boolean existsWithPrimaryKey(Long id) {
        return this.dao.existsWithPrimaryKey(id);
    }
}
