package com.Kamran.gharKaBawarchi.Service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Kamran.gharKaBawarchi.Entity.Booking;
import com.Kamran.gharKaBawarchi.Respository.BookingRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class BookingServiceTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void testFindBookingsByUserId() {
        Long userId = 1L; // Replace with a valid user ID for testing
        List<Booking> bookings = bookingRepository.findBookingsByUserId(userId);
        bookings.forEach(booking -> System.out.println("Booking: " + booking));
        System.out.println("-------------------");
    }

}
