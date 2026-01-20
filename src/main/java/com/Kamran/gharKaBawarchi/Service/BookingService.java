package com.Kamran.gharKaBawarchi.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.hibernate.type.descriptor.jdbc.LocalDateTimeJdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
import com.Kamran.gharKaBawarchi.cacheDto.BookingCacheDto;
import com.Kamran.gharKaBawarchi.cacheDto.CookDto;
import com.Kamran.gharKaBawarchi.cacheDto.MenuDto;
import com.Kamran.gharKaBawarchi.cacheDto.TimeSlotDto;
import com.Kamran.gharKaBawarchi.payment.RazorPayConfig;
import com.razorpay.Utils;

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

    @Autowired
    private RazorPayConfig razorPayConfig;

    
    public Boolean creatBooking(BookingDto bookingDto) throws Exception{
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


        if ("ONLINE".equals(bookingDto.getPaymentMode())) {
            String payload=bookingDto.getRazorpayOrderId()
                            + "|"+bookingDto.getRazorpayPaymentId();
            boolean verified=Utils.verifySignature(payload,bookingDto.getRazorpaySignature(),razorPayConfig.getKeySecret());
            if(!verified){
                System.out.println("Payment verification failed");
                return false;
               
            }
            booking.setPaymentStatus("PAID");
            booking.setRazorpayPaymentId(bookingDto.getRazorpayPaymentId());
            booking.setRazorpayOrderId(bookingDto.getRazorpayOrderId());

        }
        else{
            booking.setPaymentStatus("COD");
        }
        booking.setBookingTime(java.time.LocalDateTime.now());

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
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    @Cacheable(value ="bookings", key="#p0", unless = "#result == null")
    public BookingCacheDto getBookingById(String id){
        System.out.println("***********************");
        System.out.println("fetching from db");
        //Thread.sleep(5000);
        System.out.println("*******************");
        Booking booking= bookingRepository.findById(Long.parseLong(id)).orElse(null);

        if(booking==null){
            return null;
        }
        List<MenuDto> menuDtos = booking.getMenuItems().stream()
                                            .map(menu -> new MenuDto(
                                                menu.getMenuId(),
                                                menu.getMenuName(),
                                                menu.getPrice()
                                            )).toList();
        return new BookingCacheDto(
                        booking.getId(),
                        booking.getCustomerName(),
                        booking.getStatus(),
                        booking.getTotalAmount(),
                        booking.getPaymentStatus(),
                        booking.getNumberOfPeople(),
                        new CookDto(
                            booking.getCook().getCookId(),
                            booking.getCook().getCookName()
                        ),
                        new TimeSlotDto(
                            booking.getTimeSlot().getDate(),
                            booking.getTimeSlot().getStartTime(),
                            booking.getTimeSlot().getEndTime()
                        ),
                        menuDtos
        );
    }
}
