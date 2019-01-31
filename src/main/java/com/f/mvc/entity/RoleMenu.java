package com.f.mvc.entity;

import com.f.validation.CreateGroup;
import com.f.validation.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * User: bvsoo
 * Date: 2018/8/20
 * Time: 下午7:22
 */
@Data
public class RoleMenu implements Serializable {
    private static final long serialVersionUID = 272635626916157706L;
    @ApiModelProperty(hidden=true)
    private Long id;
    @NotNull(message = "菜单id不能为空")
    private Long menuId;//菜单id
    @NotNull(message = "角色id不能为空")
    private Long roleId;//角色Id
    @ApiModelProperty(hidden = true)
    private Long createUserId;//创建人
    @ApiModelProperty(hidden = true)
    private Date createTime;//创建时间
}
