package com.f.core;

import com.f.base.BaseAction;
import com.f.mvc.service.notify.NotifyService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author rebysfu@gmail.com
 * @description：通知回调
 * @create 2019-01-28 下午2:32
 **/
@Log4j2
public abstract class AbstractNotifyCallBackAction extends BaseAction {

    public abstract NotifyService getNotifyService();

    public String notfiyCallbak(String channleId, String merchantId, HttpServletRequest request) {
        NotifyService notifyService = getNotifyService();
        Map<String, Object> callBackInfo = null;
        String result;
        try {
            callBackInfo = notifyService.loadPayInfo(channleId, merchantId, request);
        } catch (IOException e) {
            log.error(channleId + ":" + e.getMessage());
            return notifyService.getFailedResponseData(channleId);
        }

        if (!notifyService.verifyData(channleId, merchantId, callBackInfo)) {
            result = notifyService.getFailedResponseData(channleId);
        } else if (!notifyService.isWithDrawSuccess(channleId, merchantId, callBackInfo)) {
            result = notifyService.getFailedResponseData(channleId);
        } else {
            try {
                notifyService.withDrawSuccess(channleId, merchantId, callBackInfo);
                result = notifyService.getSuccessfulResponseData(channleId);
            } catch (Exception e) {
                log.error(e.getMessage());
                // 逻辑执行失败，等同于提现回调失败，所以返回失败数据
                result = notifyService.getFailedResponseData(channleId);
            }
        }
        return result;

    }


}
