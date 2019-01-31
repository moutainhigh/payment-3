package com.f.mvc.action;

import com.f.base.BaseAction;
import com.f.constant.AppConstant;
import com.f.enums.*;
import com.f.mvc.entity.*;
import com.f.mvc.entity.RequestParam;
import com.f.mvc.service.*;
import com.f.mvc.service.withdraw.WithdrawPaymentService;
import com.f.request.task.WithDrawTask;
import com.f.thread.HttpTaskManager;
import com.f.utils.Tools;
import com.f.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author rebysfu@gmail.com
 * @description：提现服务
 * @create 2019-01-23 上午11:21
 **/
@RestController
@RequestMapping("/withdraw")
@Api(value = "/withdraw", description = "提现到银行卡")
public class WithdrawPaymentAction extends BaseAction {

    @Autowired
    private WithdrawPaymentService withdrawPaymentService;
    @Autowired
    private BankService bankService;
    @Autowired
    private AccountBankService accountBankService;
    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private ChannelMerchantService channelMerchantService;
    @Autowired
    private WithDrawRequestService withDrawRequestService;
    @Autowired
    private RequestParamService requestParamService;
    @Autowired
    private ResponseParamService responseParamService;
    @Autowired
    private SignSettingService signSettingService;
    @Autowired
    private ChanelService chanelService;

    @ApiOperation(value = "提现到银行卡", notes = "提现到银行卡")
    @RequestMapping(value = "request/{channelId}/{merchantId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVo add(@PathVariable String channelId, @PathVariable String merchantId, @RequestBody @Validated List<BankVo> bankVos) {
        Channel queryParam = new Channel();
        queryParam.setChannelId(channelId);
        Channel channelInfo = chanelService.queryOne(queryParam);
        if (channelInfo == null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("渠道信息不存在").build();
        }

        //检查商户渠道绑定关系
        ChannelMerchant channelMerchant = new ChannelMerchant();
        channelMerchant.setChannelId(channelId);
        channelMerchant.setMerchantId(merchantId);
        channelMerchant.setStatus(StateEnum.NORMAL.getCode());
        ChannelMerchant bindMerchant = channelMerchantService.queryOne(channelMerchant);
        if (bindMerchant == null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("渠道未和商户绑定或已停用，无法提现").build();
        }
        //检查提现请求
        WithDrawRequest qurryRequest = new WithDrawRequest();
        qurryRequest.setChannelId(channelId);
        qurryRequest.setType(Integer.parseInt(PayInterfaceType.MERCHANT_WITHDRAW.getValue()));
        WithDrawRequest withDrawRequest = withDrawRequestService.queryOne(qurryRequest);
        if (withDrawRequest == null) {
            return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).message("渠道没有配置提现请求").build();
        }
        //检查请求参数
        RequestParam requestParam = new RequestParam();
        requestParam.setRequestId(qurryRequest.getId());
        List<RequestParam> requestParams = requestParamService.queryListByWhere(requestParam);
        if (requestParam == null || requestParams.isEmpty()) {
            return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).message("渠道没有配置提现请求参数").build();
        }
        // 检查返回参数
        ResponseParam responseParamQuerry = new ResponseParam();
        responseParamQuerry.setRequestId(qurryRequest.getId());
        List<ResponseParam> responseParams = responseParamService.queryListByWhere(responseParamQuerry);
        if (responseParams == null || responseParams.isEmpty()) {
            return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).message("渠道没有配置提现同步返回参数配置").build();
        }

        //检查签名
        SignSetting queryParams = new SignSetting();
        queryParams.setForeignKey(withDrawRequest.getId().toString());
        SignSetting signRequest = signSettingService.queryOne(queryParams);
        SignSetting signChannel = null;
        if (signRequest == null) {
            queryParams.setForeignKey(channelInfo.getChannelId());
            signChannel = signSettingService.queryOne(queryParams);
            if (signChannel == null) {
                return ResponseVo.builder().code(HttpStatus.INTERNAL_SERVER_ERROR).message("提现请求没有配置签名信息").build();
            }
        }

        SignSetting signSetting = signRequest == null ? signChannel : signRequest;
        //检查渠道账号信息
        AccountInfo qurryOne = new AccountInfo();
        qurryOne.setChannelId(channelId);
        AccountInfo accountInfo = accountInfoService.queryOne(qurryOne);
        if (accountInfo == null) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("渠道：" + channelId + "没有设置账户信息").build();
        }

        Collection<BankVo> distinctBankVos = bankVos.stream().filter(Objects::nonNull).collect(Collectors.toMap(o -> o.getCardNo() + o.getChannel(), Function.identity(), (a, b) -> a, HashMap::new)).values();
        if (bankVos.size() != distinctBankVos.size()) {
            return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("存在重复数据，请检查").build();
        }
        // 单据组装和校验
        List<Order> orders = new ArrayList<>();
        List<AccountBank> accountBanks = new ArrayList<>();
        List<Bank> banks = new ArrayList<>();
        for (BankVo bankVo : distinctBankVos) {
            if (BankTypeEnum.getBankTypeByBankName(bankVo.getBankName()) == null) {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("错误的银行类型!").build();

            }
            if (bankVo.getAmount() > AppConstant.MAX_WITHDRAW_DEPOSIT_PER_CARD || bankVo.getAmount() < 0) {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("单张卡提现额度错误! 卡号:" + bankVo.getCardNo() + "\n 额度:" + bankVo.getAmount() + "\n 额度范围: (0,50000]").build();
            }

            //不存在关联关系的才能绑定
            AccountBank qurry = new AccountBank();
            qurry.setCardNo(bankVo.getCardNo());
            qurry.setAcountid(accountInfo.getAccountId());
            AccountBank accountBank = accountBankService.queryOne(qurry);
            if (accountBank != null) {
                return ResponseVo.builder().code(HttpStatus.BAD_REQUEST).message("卡号:" + bankVo.getCardNo() + "账号" + accountBank.getAcountid() + "绑定关系已存在").build();
            } else {
                accountBank = new AccountBank();
                accountBank.setAcountid(accountInfo.getAccountId());
                accountBank.setCardNo(bankVo.getCardNo());
                accountBank.setAmount(bankVo.getAmount());
                accountBanks.add(accountBank);
            }
            //如果银行卡不存在，则进行添加
            Bank quey = new Bank();
            quey.setCardNo(bankVo.getCardNo());
            if (bankService.queryOne(quey) == null) {
                banks.add(bankVo.transToBank());
            }
            //生成订单
            Order order = new Order();
            order.setChannelId(channelInfo.getChannelId());//渠道编码
            order.setChannelName(channelInfo.getChannelName());//渠道名称
            order.setOrderNo(Tools.Creatordernum(order.getAmount()));//系统订单号
            //order.setOutTradeNo("");//流水号
            order.setMerhtAccount(accountBank.getAcountid());//商户账号
            order.setMerchantId(bindMerchant.getMerchantId());//商户号
            order.setMerchantName(bindMerchant.getMerchantName());//固定商户名
            order.setCardNo(bankVo.getCardNo());//银行卡号
            order.setBankCode(Integer.toString(BankTypeEnum.getBankTypeByBankName(bankVo.getBankName()).getCode()));//银行编码
            order.setBankName(bankVo.getBankName());//银行名称
            order.setAccountName(bankVo.getName());//开户人
            order.setBankBranch(bankVo.getBankBranch());//开户行
            order.setAmount(accountBank.getAmount() * 100);//提现金额分
            order.setCreateTime(new Date());//提现时间
        }

        withdrawPaymentService.requestWithdrawPayment(orders, accountBanks, banks, bindMerchant);

        //发送任务
        List<RequestParamVo> requestParamVos = new ArrayList<>();
        List<ResponseParamVo> responseParamVos = new ArrayList<>();

        requestParams.forEach(param -> requestParamVos.add(param.transToVO()));
        responseParams.forEach(param -> responseParamVos.add(param.transToVO()));
        Date createTime = new Date();
        orders.stream().filter(Objects::nonNull).forEach(order -> {
            RequestContextVO requestContextVO = new RequestContextVO();
            requestContextVO.setKey(bindMerchant.getAppkey());//商户key
            requestContextVO.setMoney(order.getAmount());//提现金额
            SignSettingVo signSettingVo = new SignSettingVo();
            BeanUtils.copyProperties(signSetting, signSettingVo);
            requestContextVO.setSignSettingVo(signSettingVo);//加密信息
            Map<FieldType, Object> data = new HashMap<>();
            data.put(FieldType.ORDER_NO, order.getOrderNo());//订单号
            data.put(FieldType.MCH_ID, bindMerchant.getMerchantId());//商户号
            data.put(FieldType.BANK_CODE, order.getBankCode());//银行编码
            data.put(FieldType.BANK_NAME, order.getBankName());//银行名称
            data.put(FieldType.BANK_BRANCH, order.getBankBranch());//开户行
            data.put(FieldType.ACCOUNT_NAME, order.getAccountName());//开户人
            data.put(FieldType.DATE, createTime);//提现时间
            data.put(FieldType.PAYEE, bindMerchant.getAccountNo());//收款人账号
            data.put(FieldType.PAYEE_NAME, bindMerchant.getAccountName());//收款人名称
            data.put(FieldType.NOTIFY_URL, "/withdraw/callback/" + channelId + "/" + merchantId);//前期写死
            requestContextVO.setData(data);
            HttpTaskManager.addTask(new WithDrawTask(requestParamVos, responseParamVos,
                    requestContextVO,
                    ResponseParseType.valueOf(withDrawRequest.getParseType()), order.getOrderNo()));
        });
        return ResponseVo.builder().build();
    }
}
