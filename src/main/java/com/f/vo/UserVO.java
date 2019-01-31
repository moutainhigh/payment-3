package com.f.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author rebysfu@gmail.com
 * @description：
 * @create 2019-01-04 下午2:28
 **/
@Data
@ApiModel(value = "用户账号")
public class UserVO {
    @ApiModelProperty(value = "账号")
    @NotNull(message = "账号不能为空")
    private String account;//账号
    @NotNull(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;//密码
    @NotEmpty(message = "用户角色Id")
    @ApiModelProperty(value = "角色id数组")
    private long[] roleIds;
}
