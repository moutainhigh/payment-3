package com.f.core;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author rebysfu@gmail.com
 * @description：分页组件
 * @create 2019-01-17 下午6:17
 **/
@Data
@Builder
public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = 8656597559014685635L;
    /**
     * 当前页
     **/
    private int currentPageNo;
    /**
     * 总数
     **/
    private long total;
    /**
     * 响应携带数据
     */
    private List<T> list;    //结果集


}
