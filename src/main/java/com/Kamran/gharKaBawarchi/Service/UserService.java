package com.Kamran.gharKaBawarchi.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Dto.BookingDto;
import com.Kamran.gharKaBawarchi.Dto.LogInDto;
import com.Kamran.gharKaBawarchi.Entity.Booking;
import com.Kamran.gharKaBawarchi.Entity.BookingStatus;
import com.Kamran.gharKaBawarchi.Entity.Users;
import com.Kamran.gharKaBawarchi.Respository.UserRepository;

@Service
public class UserService {

    @Autowired
    private final CookService cookService;

    @Autowired
    private UserRepository userRepository;

    UserService(CookService cookService) {
        this.cookService = cookService;
    }

    public Boolean processLogin(LogInDto logInDto){
        Users users=userRepository.findByUserEmailIgnoreCase(logInDto.getUserName());
        if(users!=null){
            if(users.getPassword().equals(logInDto.getPassword())){
                return true;
            }
            return false;
        }
        return false;
    }
    
    public Users getUser(String email){
        Users users=userRepository.findByUserEmailIgnoreCase(email);
        return users;
    }

    public Boolean createBooking(BookingDto bookingDto , Long id){
        Booking booking=new Booking();
        // Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        // String userEmail=auth.getName();
        // Users users=userRepository.findByUserEmailIgnoreCase(userEmail);
        // booking.setUsers(users);
        booking.setCook(cookService.getCookById(id).get());
        booking.setStatus(BookingStatus.PENDING);
        return true;
    }

}
