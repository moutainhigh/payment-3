package com.f.mvc.service;
import com.f.mvc.entity.Bank;
import com.f.core.IService;
import com.f.vo.BankVo;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/01/15.
 */
public interface BankService extends IService<Bank> {
    void saveBankList(List<BankVo> bankVos);
}
