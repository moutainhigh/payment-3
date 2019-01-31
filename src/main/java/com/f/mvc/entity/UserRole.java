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
 * Date: 2018/6/28
 * Time: 下午2:00
 */
@Data
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1653932316847190291L;
    @ApiModelProperty(hidden = true)
    private Long id;//id
    @ApiModelProperty(value = "角色id")
    @NotNull(message = "用户id不能为空")
    private Long sysRoleId;//角色id
    @ApiModelProperty(value ="用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;//用户id
    @ApiModelProperty(hidden = true)
    private Long createUserId;//创建人
    @ApiModelProperty(hidden = true)
    private Date createTime;//创建时间
}
