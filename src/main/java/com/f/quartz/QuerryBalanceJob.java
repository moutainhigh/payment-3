package com.f.quartz;
/*
 *@ClassName QuartzTask
 *@Author 建国
 *@Date 1/23/19 11:57 AM
 */

import com.f.enums.RequestType;
import com.f.mvc.entity.WithDrawRequest;
import com.f.mvc.service.WithDrawRequestService;
import com.f.request.RequestService;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Log4j2
public class QuerryBalanceJob implements Job {
    @Autowired
    private WithDrawRequestService withDrawRequestService;
    @Autowired
    private RequestService requestService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        WithDrawRequest withDrawRequest = new WithDrawRequest();
        withDrawRequest.setType(RequestType.CHECK_BALANCE.ordinal());
        List<WithDrawRequest> lists = withDrawRequestService.queryListByWhere(withDrawRequest);
        lists.forEach(request->{
            requestService.submitQuerryBalanceTask(request);
        });
    }
}
