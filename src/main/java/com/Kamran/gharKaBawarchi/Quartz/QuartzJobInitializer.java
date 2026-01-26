package com.Kamran.gharKaBawarchi.Quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class QuartzJobInitializer {
    private final Scheduler scheduler;
    public QuartzJobInitializer(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void scheduleJob() throws SchedulerException{

        JobDetail job=JobBuilder.newJob(inProgressOrderJob.class)
                        .withIdentity("inprogressBookingJob")
                        .storeDurably()
                        .build();

        Trigger Trigger=TriggerBuilder.newTrigger()
                        .withIdentity("inprogressBookingTrigger")
                        .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(1))
                        .startNow()
                        .build();
        scheduler.scheduleJob(job, Trigger);


    }
}
