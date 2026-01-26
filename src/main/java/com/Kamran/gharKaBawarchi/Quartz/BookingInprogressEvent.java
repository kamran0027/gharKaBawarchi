package com.Kamran.gharKaBawarchi.Quartz;
import org.springframework.context.ApplicationEvent;

import com.Kamran.gharKaBawarchi.Entity.Booking;


/// this is spring Event   
public class BookingInprogressEvent extends ApplicationEvent {

    private final Booking booking;

    public BookingInprogressEvent(Object source, Booking booking) {
        super(source);
        this.booking = booking;
    }
    public Booking getBooking() {
        return booking;
    }
}
