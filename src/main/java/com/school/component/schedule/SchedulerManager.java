//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.component.schedule;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

//@Component
public class SchedulerManager {
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public SchedulerManager() {
    }

//    public void startJob(LocalDateTime startTime, String jobName, String jobGroup, Class<? extends Job> jobClass) throws SchedulerException {
//        Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
//        this.deleteJobIfExist(jobName, jobGroup);
//        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
//        Trigger trigger = TriggerBuilder.newTrigger().startAt(Date.from(startTime.atZone(ZoneOffset.ofHours(8)).toInstant())).build();
//        scheduler.scheduleJob(jobDetail, trigger);
//    }
//
//    public void startJob(Date startTime, String jobName, String jobGroup, Class<? extends Job> jobClass) throws SchedulerException {
//        Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
//        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
//        Trigger trigger = TriggerBuilder.newTrigger().startAt(startTime).build();
//        scheduler.scheduleJob(jobDetail, trigger);
//    }
//
//    public void deleteJobIfExist(String jobName, String jobGroup) throws SchedulerException {
//        this.deleteJobIfExist(new JobKey(jobName, jobGroup));
//    }
//
//    public void deleteJobIfExist(JobKey jobKey) throws SchedulerException {
//        Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
//        if (scheduler.checkExists(jobKey)) {
//            scheduler.deleteJob(jobKey);
//        }
//
//    }
//
//    public void clearAll() throws SchedulerException {
//        Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
//        scheduler.clear();
//    }
}
