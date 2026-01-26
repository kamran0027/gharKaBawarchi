package com.Kamran.gharKaBawarchi.Quartz;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Entity.Booking;
import com.Kamran.gharKaBawarchi.Entity.Enum.BookingStatus;
import com.Kamran.gharKaBawarchi.Respository.BookingRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingInprogressService {
    
    private final BookingRepository bookingRepository;
    private final ApplicationEventPublisher publisher;

    public BookingInprogressService(BookingRepository bookingRepository, ApplicationEventPublisher publisher) {
        this.bookingRepository = bookingRepository;
        this.publisher = publisher;
    }

    @Transactional
    public void inProgressOrder(){
        List<Booking> bookings = bookingRepository.findByStatusAndBookingTimeBefore(BookingStatus.CONFIRMED,LocalDateTime.now());
        System.out.println("****************************");
        
        if (bookings.size()>=1) {
            for(Booking booking : bookings){
                booking.setStatus(BookingStatus.INPROGRESS);
                bookingRepository.save(booking);

                //publis event

                publisher.publishEvent(new BookingInprogressEvent(this, booking));
            }
        }
        
    }
}