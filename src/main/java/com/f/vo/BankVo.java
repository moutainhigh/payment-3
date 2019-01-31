package com.f.vo;

import com.f.enums.BankTypeEnum;
import com.f.mvc.entity.Bank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/*
 *@ClassName BankVo
 *@Author 建国
 *@Date 1/15/19 11:26 AM
 */
@Data
@ApiModel(value = "银行卡")
public class BankVo {
    @NotNull(message = "渠道为空")
    @ApiModelProperty(value = "绑定的渠道")
    String channel;
    @NotNull(message = "金额不能为空")
    @ApiModelProperty(value = "金额")
    int amount;
    @NotNull(message = "银行名字不能为空")
    @ApiModelProperty(value = "银行名字")
    String bankName;
    @NotNull(message = "银行卡号不能为空")
    @ApiModelProperty(value = "银行卡号")
    long  cardNo;
    @NotNull(message = "开户人不能为空")
    @ApiModelProperty(value = "开户人")
    String name;
    @NotNull(message = "开户行不能为空")
    @ApiModelProperty(value = "开户行")
    String bankBranch;
    @NotNull(message = "省不能为空")
    @ApiModelProperty(value = "省")
    String province;
    @NotNull(message = "市不能为空")
    @ApiModelProperty(value = "市")
    String city;

    public Bank transToBank(){
       Bank bank = new Bank();
       bank.setBankCode(Integer.toString(BankTypeEnum.getBankTypeByBankName(getBankName()).getCode()));
       bank.setBankName(getBankName());
       bank.setCardNo(getCardNo());
       bank.setName(getName());
       bank.setBankBranch(getBankBranch());
       bank.setProvince(getProvince());
       bank.setCity(getCity());
       bank.setCounty("");
       return bank;
    }
}
