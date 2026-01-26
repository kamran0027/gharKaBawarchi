package com.Kamran.gharKaBawarchi.Quartz;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.Kamran.gharKaBawarchi.Entity.Booking;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Retryable(
        retryFor = Exception.class,
        maxAttempts = 3,
        backoff = @Backoff(delay = 5000)
    )
    public void sendBookingInprogressEmail(Booking booking) throws MessagingException {

        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);

        Context context=new Context();
        context.setVariable("booking_id", booking.getId());
        context.setVariable("cook_name",booking.getCook().getCookName());
        context.setVariable("status",booking.getStatus());

        String html= templateEngine.process("booking_inprogress", context);

        try {
            helper.setTo(booking.getUsers().getUserEmail());
            helper.setSubject("Your booking status is update");
            helper.setText(html,true);
            javaMailSender.send(message);
        } catch (Exception e) {
            // TODO: handle exception

            throw new RuntimeException(e.getMessage());
        }


        
    }

    @Retryable(
        retryFor = Exception.class,
        maxAttempts = 3,
        backoff = @Backoff(delay = 5000)
    )
    public void sendBookingPlacedEmail(Booking booking) throws MessagingException {

        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);

        Context context=new Context();
        context.setVariable("booking_id", booking.getId());
        context.setVariable("cook_name",booking.getCook().getCookName());
        context.setVariable("status",booking.getStatus());
        context.setVariable("time", booking.getBookingTime());
        context.setVariable("menuItem",booking.getMenuItems());
        context.setVariable("totalAmount",booking.getTotalAmount());
        context.setVariable("numberOfPeople", booking.getNumberOfPeople());

        String html= templateEngine.process("booking_placed_mail", context);

        try {
            helper.setTo(booking.getUsers().getUserEmail());
            helper.setSubject("Your booking has been placed successfully");
            helper.setText(html,true);
            javaMailSender.send(message);
        } catch (Exception e) {
            // TODO: handle exception

            throw new RuntimeException(e.getMessage());
        }
    }
}