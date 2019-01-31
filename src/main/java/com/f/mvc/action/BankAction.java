package com.f.mvc.action;

import com.f.base.BaseAction;
import com.f.constant.AppConstant;
import com.f.enums.BankTypeEnum;
import com.f.mvc.entity.AccountBank;
import com.f.mvc.entity.AccountInfo;
import com.f.mvc.service.AccountBankService;
import com.f.mvc.service.AccountInfoService;
import com.f.vo.BankVo;
import com.f.vo.ResponseVo;
import com.f.mvc.entity.Bank;
import com.f.mvc.service.BankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CodeGenerator on 2019/01/15.
 */
@RestController
@RequestMapping("/bank")
@Api(value = "/bank", description = "银行卡相关操作")
public class BankAction extends BaseAction {
    @Autowired
    private BankService bankService;
    @Autowired
    private AccountBankService accountBankService;
    @Autowired
    private AccountInfoService accountInfoService;

    @ApiOperation(value = "/add", notes = "添加银行卡信息")
    @RequestMapping(value = "/add",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    public ResponseVo add(@RequestBody List<BankVo> bankVos) {
        List<BankVo>removeList = new ArrayList<>();
        //需要新增的bankAccount信息
        List<BankVo>bankAccountList = new ArrayList<>();

        for (BankVo bankVo : bankVos){
            if(BankTypeEnum.getBankTypeByBankName(bankVo.getBankName())==null)
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("错误的银行类型!").build();
            if(bankVo.getAmount() > AppConstant.MAX_WITHDRAW_DEPOSIT_PER_CARD || bankVo.getAmount() < 0)
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("单张卡提现额度错误! 卡号:" + bankVo.getCardNo() +"\n 额度:"+bankVo.getAmount() +"\n 额度范围: (0,50000]").build();

            AccountInfo qurryOne = new AccountInfo();
            qurryOne.setChannelId(bankVo.getChannel());
            AccountInfo accountInfo = accountInfoService.queryOne(qurryOne);
            if(accountInfo == null)
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("不存在渠道号! 渠道：" + bankVo.getChannel()).build();
            //不存在关联关系的才能绑定
            AccountBank qurry = new AccountBank();
            qurry.setCardNo(bankVo.getCardNo());
            qurry.setAcountid(accountInfo.getAccountId());
            if(accountBankService.queryOne(qurry) == null)
                bankAccountList.add(bankVo);
            //已经存在的银行卡不做存入操作
            BankVo qurryBank = new BankVo();
            bankVo.setCardNo(bankVo.getCardNo());
            if(bankService.queryOne(bankVo.transToBank()) != null)
                removeList.add(bankVo);

        }

        bankVos.removeAll(removeList);
        for (BankVo bankVo : bankAccountList){
            AccountBank accountBank = new AccountBank();
            AccountInfo qurryOne = new AccountInfo();
            qurryOne.setChannelId(bankVo.getChannel());
            AccountInfo accountInfo = accountInfoService.queryOne(qurryOne);
            accountBank.setAcountid(accountInfo.getAccountId());
            accountBank.setAmount(bankVo.getAmount());
            accountBank.setCardNo(bankVo.getCardNo());
            accountBankService.save(accountBank);
        }
        bankService.saveBankList(bankVos);
        return ResponseVo.builder().data(null).build();
    }

    /**
     * 查询所有银行类型列表以及详情
     *
     * @return
     */
    @ApiOperation(value = "/queryBankType", notes = "查询所有银行类型列表")
    @RequestMapping(value = "/queryBankType",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo query() {
        return ResponseVo.builder().data(BankTypeEnum.getBankTypeList()).build();
    }

}
