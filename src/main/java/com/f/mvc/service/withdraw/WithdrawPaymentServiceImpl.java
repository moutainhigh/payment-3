package com.f.mvc.service.withdraw;

import com.f.mvc.dao.AccountBankDao;
import com.f.mvc.dao.BankDao;
import com.f.mvc.dao.OrderDao;
import com.f.mvc.entity.AccountBank;
import com.f.mvc.entity.Bank;
import com.f.mvc.entity.ChannelMerchant;
import com.f.mvc.entity.Order;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author rebysfu@gmail.com
 * @description：
 * @create 2019-01-23 上午11:11
 **/
@Log4j2
@Service
@Transactional(rollbackFor = Exception.class)
public class WithdrawPaymentServiceImpl implements WithdrawPaymentService {

    @Resource
    private BankDao bankDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    private AccountBankDao accountBankDao;

    @Override
    public void requestWithdrawPayment(List<Order> orders, List<AccountBank> accountBanks, List<Bank> banks, ChannelMerchant bindMerchant) {
        bankDao.insertList(banks);
        accountBankDao.insertList(accountBanks);
        orderDao.insertList(orders);
    }
}
