package com.f;
/*
 *@ClassName APPDestroy
 *@Author 建国
 *@Date 1/16/19 6:07 PM
 */

import com.f.quartz.SchedulerManager;
import com.f.thread.HttpTaskManager;
import com.f.utils.HttpConnectionPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
@Component
public class APPDestroy {
    @Autowired
    private SchedulerManager schedulerManager;
    @PreDestroy
    public void destroy(){
        HttpTaskManager.destroyTaskPool();
        HttpConnectionPoolUtil.closeConnectionPool();
        schedulerManager.clearAll();
    }
}
