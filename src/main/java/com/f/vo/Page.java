package com.f.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author rebysfu@gmail.com
 * @description：分页请求
 * @create 2019-01-17 下午5:18
 **/
@ApiModel("分页参数")
@Data
public class Page {
    /**
     * 页码，从1开始
     */
    @ApiModelProperty("页码")
    private Integer pageNum=1;
    /**
     * 页面大小
     */
    @ApiModelProperty("页面大小")
    private Integer pageSize=10;
    /**
     * 查询的key
     */
    @ApiModelProperty("查询的key")
    private String keyword;
}
