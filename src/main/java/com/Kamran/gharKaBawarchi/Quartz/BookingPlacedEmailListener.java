package com.Kamran.gharKaBawarchi.Quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;

@Component
public class BookingPlacedEmailListener {
    @Autowired
    private EmailService emailService;

    @Async
    @EventListener
    public void handleBookingPlaced(BookingPlacedEvent event) throws MessagingException {
        emailService.sendBookingPlacedEmail(event.getBooking());
    }
}
