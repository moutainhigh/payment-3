package com.f.mvc.service;

import com.f.mvc.dao.BankDao;
import com.f.mvc.entity.Bank;
import com.f.mvc.service.BankService;
import com.f.core.BaseService;
import com.f.vo.BankVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/01/15.
 */
@Service
@Transactional
public class BankServiceImpl extends BaseService<Bank> implements BankService {
    @Resource
    private BankDao bankDao;

    @Override
    public void saveBankList(List<BankVo> bankVos) {
        for(BankVo bank:bankVos)
            this.save(bank.transToBank());
    }
}
