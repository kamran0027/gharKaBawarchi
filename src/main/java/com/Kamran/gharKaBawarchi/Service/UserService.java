package com.Kamran.gharKaBawarchi.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Dto.LogInDto;
import com.Kamran.gharKaBawarchi.Entity.Booking;
import com.Kamran.gharKaBawarchi.Entity.Users;
import com.Kamran.gharKaBawarchi.Respository.BookingRepository;
import com.Kamran.gharKaBawarchi.Respository.UserRepository;

@Service
public class UserService {

    @Autowired
    private final CookService cookService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

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

    public List<Booking> getAllOrderByUserId(Long userId){
        return bookingRepository.findBookingsByUserId(userId);

    } 

}
