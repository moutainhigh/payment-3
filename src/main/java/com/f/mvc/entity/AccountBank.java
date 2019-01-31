package com.f.mvc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * 银行账户关联信息表
 * 
 * @author CaiBi
 * 
 * @date 2019-01-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_account_bank")
public class AccountBank {
    /** 第三方平台账户id */
    private String acountid;

    /** 银行卡号 */
    private Long cardNo;

    /**
     * 交易金额(单位:分)
     */
    private Integer amount;
}