package com.f.mvc.entity;

import com.f.validation.CreateGroup;
import com.f.validation.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * User: bvsoo
 * Date: 2018/8/20
 * Time: 下午7:20
 */
@Data
@ApiModel(value = "菜单")
public class Menu implements Serializable {
    private static final long serialVersionUID = -2114737749281506039L;
    @ApiModelProperty(value = "id")
    @NotNull(message = "菜单Id不能为空",groups = UpdateGroup.class )
    private Long id;
    @NotNull(message = "菜单url不能为空",groups = {CreateGroup.class, UpdateGroup.class} )
    @ApiModelProperty(value = "菜单url")
    private String url;
    @NotNull(message = "菜单名称不能为空",groups = {CreateGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "菜单名称")
    private String name;
    @ApiModelProperty(hidden = true)
    private Integer type;//1/0 固化菜单/添加菜单
    @ApiModelProperty(hidden = true)
    private Long createUserId;
    @ApiModelProperty(hidden = true)
    private Date createTime;
    @ApiModelProperty(hidden = true)
    private Long modifyUserId;
    @ApiModelProperty(hidden = true)
    private Date modifyTime;
}
