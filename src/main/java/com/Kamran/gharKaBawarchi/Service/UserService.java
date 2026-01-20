package com.Kamran.gharKaBawarchi.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Dto.RegistrationDto;
import com.Kamran.gharKaBawarchi.Dto.UserProfileDto;
import com.Kamran.gharKaBawarchi.Entity.Address;
import com.Kamran.gharKaBawarchi.Entity.Booking;
import com.Kamran.gharKaBawarchi.Entity.City;
import com.Kamran.gharKaBawarchi.Entity.Users;
import com.Kamran.gharKaBawarchi.Entity.Enum.Roles;
import com.Kamran.gharKaBawarchi.Respository.BookingRepository;
import com.Kamran.gharKaBawarchi.Respository.CityRepository;
import com.Kamran.gharKaBawarchi.Respository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Users getUser(String email){
        Users users=userRepository.findByUserEmailIgnoreCase(email);
        return users;
    }

    public List<Booking> getAllOrderByUserName(String userName){
        return bookingRepository.findByUsers(userRepository.findByUserEmailIgnoreCase(userName));
        //return bookingRepository.findBookingsByUserId(userId);

    }

    public Users saveRegistration(RegistrationDto registrationDto){
        Users user=new Users();
        user.setRole(Roles.USER);
        Address address=new Address();
        address.setStreet(registrationDto.getAddressDto().getStreet());
        address.setCity(registrationDto.getAddressDto().getCity());
        address.setState(registrationDto.getAddressDto().getState());
        address.setZipCode(registrationDto.getAddressDto().getZipCode());
        address.setCountry(registrationDto.getAddressDto().getCountry());
        user.setUserEmail(registrationDto.getEmail());
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setAddress(address);
        user.setFullName(registrationDto.getFullName());
        // setting the city for service
        Optional<City> city=cityRepository.findById(registrationDto.getCityId());
        user.setCity(city.get());
        // set the addres when we save the user beacuse the cascade type is all

        return userRepository.save(user);
    }

    @Transactional
    public boolean updateProfile(UserProfileDto userProfileDto){
        Users user=userRepository.findByUserEmailIgnoreCase(userProfileDto.getEmail());
        if (user!=null) {
            user.setFullName(userProfileDto.getFullName());
            user.setPhoneNumber(userProfileDto.getPhoneNumber());
            // if (userProfileDto.getPassword().length()!=0 && userProfileDto.getPassword().charAt(0)!=' ') {
            //     user.setPassword(userProfileDto.getPassword());
            // }
            
            userRepository.save(user);
            return true;
        }
        return false;
        

    }
    public Page<Users> getUserByPaging(int pageNo){
        return  userRepository.findAll(PageRequest.of(pageNo,5));
    }

    public List<Booking> getAllOrderByUserId(Long userId){
        return bookingRepository.findBookingsByUserId(userId);
    }

}
