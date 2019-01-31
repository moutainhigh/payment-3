package com.f.quartz;
/*
 *@ClassName MinuteJob
 *@Author 建国
 *@Date 1/23/19 2:52 PM
 */

import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
@Log4j2
public class MinuteJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("分钟定时器执行");
    }
}
