package com.f.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/*
 *@ClassName BankTypeVo
 *@Author 建国
 *@Date 1/15/19 11:06 AM
 */
@Data
public class BankTypeVo {
    //@ApiModelProperty(value = "银行编号")
   // private int code;
    @ApiModelProperty(value = "银行全称")
    private String name;
   // @ApiModelProperty(value = "银行简称")
   // private String shortName;
}
