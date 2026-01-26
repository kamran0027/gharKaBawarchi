package com.Kamran.gharKaBawarchi.Quartz;

import org.springframework.context.ApplicationEvent;

import com.Kamran.gharKaBawarchi.Entity.Booking;

public class BookingPlacedEvent extends ApplicationEvent {

    private final Booking booking;

    public BookingPlacedEvent(Object source,Booking booking){
        super(source);
        this.booking = booking;
    }

    public Booking getBooking() {
        return booking;
    }

}
