package com.Kamran.gharKaBawarchi.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Dto.BookingDto;
import com.Kamran.gharKaBawarchi.Entity.Booking;
import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Entity.Menu;
import com.Kamran.gharKaBawarchi.Entity.TimeSlot;
import com.Kamran.gharKaBawarchi.Entity.Users;
import com.Kamran.gharKaBawarchi.Entity.Enum.BookingStatus;
import com.Kamran.gharKaBawarchi.Respository.BookingRepository;
import com.Kamran.gharKaBawarchi.Respository.CookRepository;
import com.Kamran.gharKaBawarchi.Respository.MenuRepository;
import com.Kamran.gharKaBawarchi.Respository.TimeSlotRepository;

@Service
public class BookingService {

    @Autowired
    private CookRepository cookRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private  CookService cookService;
    @Autowired
    private  UserService userService;
    @Autowired
    private  BookingRepository bookingRepository;

    @Autowired
    private  TimeSlotRepository timeSlotRepository;

    
    public Boolean creatBooking(BookingDto bookingDto){
        Booking booking=new Booking();
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String userEmail=auth.getName();
        Users user=userService.getUser(userEmail);
        booking.setUsers(user);
        // setting the cook to booking kk
        Optional<Cook> cook=cookRepository.findById(bookingDto.getCookId());
        booking.setCook(cook.get());

        // LocalDateTime time=new LocalDateTime();
        // booking.setBookingTIme(ti);

        TimeSlot timeSlot=timeSlotRepository.findById(bookingDto.getSlotId()).orElse(null);
        if(timeSlot.isBooked()){
            return false;
        }
        timeSlot.setBooked(true);
        booking.setTimeSlot(timeSlot);

        // mapping food item to booking 
        List<Menu> menuItems=new ArrayList<>();
        for(Long menuId:bookingDto.getFoodItemIds()){
            Menu menu=menuRepository.findById(menuId).orElse(null);
            if(menu!=null){
                menuItems.add(menu);
            }
        }
        booking.setMenuItems(menuItems);

        booking.setTotalAmount(bookingDto.getTotalAmount());
        booking.setCustomerName(bookingDto.getCustomerName());
        booking.setNumberOfPeople(bookingDto.getNumberOfPeople());
        booking.setStatus(BookingStatus.PENDING);

        bookingRepository.save(booking);
        return true;
    }

    public boolean cancleBooking(Long bookingId){
        Booking booking=bookingRepository.findById(bookingId).orElse(null);
        if(booking==null){
            return false;
        }
        //available that slot
        TimeSlot timeSlot=booking.getTimeSlot();
        timeSlot.setBooked(false);
        timeSlotRepository.save(timeSlot);
        //deleting booking from only user
        booking.setUsers(null);
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        return true;
        
        
    }
}
