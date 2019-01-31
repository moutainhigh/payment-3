package com.f.mvc.service.withdraw;

import com.f.mvc.entity.AccountBank;
import com.f.mvc.entity.Bank;
import com.f.mvc.entity.ChannelMerchant;
import com.f.mvc.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rebysfu@gmail.com
 * @description： 提现服务
 * @create 2019-01-16 上午11:38
 **/
@Service
public interface WithdrawPaymentService {
    /*
     * @Author rebysfu@gmail.com
     * @Description 发起提现请求
     * @Date 下午2:50 2019/1/23
     * @Param [orders, accountBanks, banks,bindMerchant]
     * @return void
     **/
    void requestWithdrawPayment(List<Order> orders, List<AccountBank> accountBanks, List<Bank> banks, ChannelMerchant bindMerchant);
}
