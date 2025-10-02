package com.Kamran.gharKaBawarchi.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Dto.BookingDto;
import com.Kamran.gharKaBawarchi.Entity.Booking;
import com.Kamran.gharKaBawarchi.Entity.BookingStatus;
import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Entity.Menu;
import com.Kamran.gharKaBawarchi.Entity.Users;
import com.Kamran.gharKaBawarchi.Respository.BookingRepository;
import com.Kamran.gharKaBawarchi.Respository.CookRepository;
import com.Kamran.gharKaBawarchi.Respository.MenuRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingService {

    @Autowired
    private CookRepository cookRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private final CookService cookService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final BookingRepository bookingRepository;

    BookingService(CookService cookService, UserService userService, BookingRepository bookingRepository) {
        this.cookService = cookService;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public Boolean creatBooking(BookingDto bookingDto){
        Booking booking=new Booking();
        //mapping user to booking
        // Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        // String userEmail=auth.getName();
        // Users users=userRepository.findByUserEmailIgnoreCase(userEmail);
        // booking.setUsers(users);
        Users user=userService.getUser("test@gmail.com");
        booking.setUsers(user);
        // setting the cook to booking 
        Optional<Cook> cook=cookRepository.findById(bookingDto.getCookId());
        booking.setCook(cook.get());

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
}
