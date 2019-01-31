package com.f.quartz;

import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/*
 *@ClassName SchedulerManager
 *@Author 建国
 *@Date 1/23/19 2:11 PM
 */

@Log4j2
@Service
public class SchedulerManager {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    private JobListener scheduleListener;

    @PostConstruct
    public void init(){
        startJob("0/5 * * * * ?","qurryBalanceJob","qurryBalanceJob", QuerryBalanceJob.class);//每五秒执行一次
    }

    /**
     * 开始定时任务
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    public void startJob(String cron,String jobName,String jobGroup,Class<? extends Job> jobClass)
    {
        try{
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            if(scheduleListener==null){
                scheduleListener=new SchedulerListener();
                scheduler.getListenerManager().addJobListener(scheduleListener);
            }
            JobKey jobKey=new JobKey(jobName,jobGroup);
            if(!scheduler.checkExists(jobKey))
            {
                scheduleJob(cron,scheduler,jobName,jobGroup,jobClass);
            }
        }catch (Exception e){
            log.error("quartz start job failed--");
        }
    }

    /**
     * 移除定时任务
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    public void deleteJob(String jobName,String jobGroup) throws SchedulerException
    {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey=new JobKey(jobName,jobGroup);
        scheduler.deleteJob(jobKey);
    }
    /**
     * 暂停定时任务
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    public void pauseJob(String jobName,String jobGroup) throws SchedulerException
    {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey=new JobKey(jobName,jobGroup);
        scheduler.pauseJob(jobKey);
    }
    /**
     * 恢复定时任务
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    public void resumeJob(String jobName,String jobGroup) throws SchedulerException
    {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey triggerKey=new JobKey(jobName,jobGroup);
        scheduler.resumeJob(triggerKey);
    }
    /**
     * 清空所有当前scheduler对象下的定时任务【目前只有全局一个scheduler对象】
     * @throws SchedulerException
     */
    public void clearAll() {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.clear();
        } catch (Exception e){
            log.error("shutdown quartz clear all failed ");
        }

    }



    /**
     * 动态创建Job
     * 此处的任务可以配置可以放到properties或者是放到数据库中
     * Trigger:name和group 目前和job的name、group一致，之后可以扩展归类
     * @param scheduler
     * @throws SchedulerException
     */
    private void scheduleJob(String cron,Scheduler scheduler,String jobName,String jobGroup,Class<? extends Job> jobClass) throws SchedulerException{
        /*
         *  此处可以先通过任务名查询数据库，如果数据库中存在该任务，更新任务的配置以及触发器
         *  如果此时数据库中没有查询到该任务，则按照下面的步骤新建一个任务，并配置初始化的参数，并将配置存到数据库中
         */
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
        // 每5s执行一次
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }


    public static void main(String[] args){
        /*try {
            SchedulerManager myScheduler = new SchedulerManager();
            myScheduler.startJob("0/5 * * * * ?","job2","group2", ScheduledJob.class);//每五秒执行一次
            //0 0/5 14 * * ?在每天下午2点到下午2:55期间的每5分钟触发
            //0 50 14 * * ?在每天下午2点50分5秒执行一次
//            myScheduler.startJob("5 50 14 * * ?","job2","group2", ScheduledJob.class);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }*/
    }
}
