package com.f.mvc.action;

import com.f.core.AbstractNotifyCallBackAction;
import com.f.mvc.service.notify.NotifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author rebysfu@gmail.com
 * @description：提现回调接口
 * @create 2019-01-25 下午3:03
 **/
@Log4j2
@Api(value = "提现异步回调接口", description = "渠道调用系统接口进行提现结果通知")
@RestController
@RequestMapping(value = "/withdraw")
public class WithDrawCallbakAction extends AbstractNotifyCallBackAction {
    @Resource(name = "withDrawNotifyService")
    private NotifyService notifyService;

    @ApiOperation(value = "提现回调接口", notes = "channleIdAndMerchantId 渠道编码和商户号之间用下划线隔开")
    @RequestMapping(value = "/callback/{channelId}/{merchantId}")
    @ResponseBody
    public String updateOrder(@PathVariable String channleId, @PathVariable String merchantId, HttpServletRequest request) {
        return super.notfiyCallbak(channleId, merchantId, request);
    }

    @Override
    public NotifyService getNotifyService() {
        return this.notifyService;
    }
}
