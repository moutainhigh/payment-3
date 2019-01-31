package com.f.quartz;
/*
 *@ClassName SchedulerListener
 *@Author 建国
 *@Date 1/23/19 2:12 PM
 */

import lombok.extern.log4j.Log4j2;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

@Log4j2
public class SchedulerListener implements JobListener {
    public static final String LISTENER_NAME = "QuartSchedulerListener";

    @Override
    public String getName() {
        return LISTENER_NAME; //must return a name
    }

    //任务被调度前
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().toString();
        //System.out.println("jobToBeExecuted");
       // System.out.println("Job : " + jobName + " is going to start...");
    }

    //任务调度被拒了
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        //System.out.println("jobExecutionVetoed");
        log.warn("jobExecutionVetoed");
    }

    //任务被调度后
    @Override
    public void jobWasExecuted(JobExecutionContext context,
                               JobExecutionException jobException) {
        //System.out.println("jobWasExecuted");

        String jobName = context.getJobDetail().getKey().toString();
       // System.out.println("Job : " + jobName + " is finished...");

        if (jobException!=null&&!jobException.getMessage().equals("")) {
            log.error("Exception thrown by: " + jobName
                    + " Exception: " + jobException.getMessage());
        }

    }
}
