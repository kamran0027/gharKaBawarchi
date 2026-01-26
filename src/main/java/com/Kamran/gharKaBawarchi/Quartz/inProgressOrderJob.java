package com.Kamran.gharKaBawarchi.Quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class inProgressOrderJob implements Job {

    @Autowired
    private BookingInprogressService bookingInprogressService;  

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // service 
        bookingInprogressService.inProgressOrder();

    }

}
