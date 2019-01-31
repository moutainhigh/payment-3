package com.f.core;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author rebysfu@gmail.com
 * @description：
 * @create 2019-01-05 下午6:17
 **/
public abstract class BaseDao<T> implements IDao<T> {
    //这里利用了Spring4才支持的泛型注入
    @Autowired
    private MyMapper<T> mapper;

    /**
     * 根据id查询
     */
    public T queryById(Long id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    /**
     * 根据条件查询一条数据
     */
    public T queryOne(T example) {
        return this.mapper.selectOne(example);
    }

    /**
     * 查询所有数据
     */
    public List<T> queryAll() {
        return this.mapper.select(null);
    }

    /**
     * 根据条件查询数据列表
     */
    public List<T> queryListByWhere(T example) {
        return this.mapper.select(example);
    }

    /**
     * 分页查询数据列表
     */
    public PageInfo<T> queryPageListByWhere(T example) {
        List<T> list = this.mapper.select(example);
        return new PageInfo<T>(list);
    }
    /**根据Example 查询*/
    public List<T> queryListByExample(Object example) {
        return this.mapper.selectByExample(example);
    }

    /**
     * 分页查询数据列表
     */
    public PageInfo<T> queryPageListByExample(Object example) {
        List<T> list = this.mapper.selectByExample(example);
        return new PageInfo<T>(list);
    }

    /**
     * 新增数据，注意设置数据的创建和更新时间
     * 返回成功的条数
     */
    public Integer save(T entity) {
        return this.mapper.insertSelective(entity);

    }

    /**
     * 更新数据，设置数据的更新时间
     * 返回成功的条数
     */
    public Integer update(T t) {
        return this.mapper.updateByPrimaryKey(t);
    }

    /**
     * 更新数据，设置数据的更新时间（更新部分数据）
     * 返回成功的条数
     */
    public Integer updateSelective(T entity) {
        return this.mapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 根据id删除数据
     */
    public Integer deleteById(final Long id) {
        return this.mapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除数据
     */
    public Integer deleteByIds(final Example example) {
        return this.mapper.deleteByExample(example);
    }

    /**
     * 根据条件删除数据
     */
    public Integer deleteByWhere(T example) {
        return this.mapper.delete(example);
    }

    /**
     * 批量插入
     **/
    public Integer insertList(List<T> list) {
        return this.mapper.insertList(list);
    }

    public boolean existsWithPrimaryKey(Long id) {
        return mapper.existsWithPrimaryKey(id);
    }
}
