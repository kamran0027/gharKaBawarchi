package com.Kamran.gharKaBawarchi.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Entity.City;
import com.Kamran.gharKaBawarchi.Entity.Users;
import com.Kamran.gharKaBawarchi.Respository.CityRepository;
import com.Kamran.gharKaBawarchi.Respository.UserRepository;

@Service
public class CityService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    public boolean updateCity(Long cityId){
        Optional<City> city= cityRepository.findById(cityId);
        if (city.isPresent()) {
            Authentication auth=SecurityContextHolder.getContext().getAuthentication();
            String userEmail=auth.getName();
            Users user=userService.getUser(userEmail);
            if(user!=null){
                user.setCity(city.get());
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
    public List<City> getAllCitys(){
        return cityRepository.findAll();
    }
    
}
