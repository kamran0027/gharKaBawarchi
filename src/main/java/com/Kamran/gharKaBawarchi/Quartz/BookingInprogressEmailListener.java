package com.Kamran.gharKaBawarchi.Quartz;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;

@Component
public class BookingInprogressEmailListener {
    private final EmailService emailService;

    public BookingInprogressEmailListener(EmailService emailService) {
        this.emailService = emailService;
    }


    @Async
    @EventListener
    public void handleBookingInprogress(BookingInprogressEvent event) throws MessagingException {
        emailService.sendBookingInprogressEmail(event.getBooking());
    }
}
