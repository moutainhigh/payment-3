package com.f.core;


import java.util.List;

/**
 * @author rebysfu@gmail.com
 * @description：
 * @create 2019-01-05 下午5:48
 **/
public interface IService<T> {
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
    public List<T> queryListByWhere(T entity);

    /**
     * 分页查询数据列表
     *
     * @param entity  查询条件
     * @param pageNum  页数
     * @param PageSize 页面大小
     * @return
     */
    public PageInfo<T> queryPageListByWhere(T entity, Integer pageNum, Integer PageSize);
    /**根据Example 查询*/
    public List<T> queryListByExample(Object example);

    /**
     * 分页查询数据列表
     */
    public PageInfo<T> queryPageListByExample(Object example, Integer page, Integer rows);
    /**
     * 新增数据，注意设置数据的创建和更新时间
     * 返回成功的条数
     */
    public Integer save(T t);

    /**
     * 更新数据，设置数据的更新时间
     * 返回成功的条数
     */
    public Integer update(T t);

    /**
     * 更新数据，设置数据的更新时间（更新部分数据）
     * 返回成功的条数
     */
    public Integer updateSelective(T t);

    /**
     * 根据id删除数据
     */
    public Integer deleteById(Long id);

    /**
     * 批量删除数据
     *
     * @param clazz
     * @param property
     * @param list
     * @return
     */
    public Integer deleteByIds(Class<T> clazz, String property, List<Object> list);

    /**
     * 根据条件删除数据
     */
    public Integer deleteByWhere(T entity);

    /**
     * 批量插入
     **/
    public Integer insertList(List<T> list);
    /**
     * 判断数据是否存在
     **/
    public boolean existsWithPrimaryKey(Long id);
}
