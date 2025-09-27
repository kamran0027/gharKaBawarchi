package com.Kamran.gharKaBawarchi.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Dto.LogInDto;
import com.Kamran.gharKaBawarchi.Entity.Users;
import com.Kamran.gharKaBawarchi.Respository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Boolean processLogin(LogInDto logInDto){
        Users users=userRepository.findByUserEmailIgnoreCase(logInDto.getUserName());
        if(users!=null){
            if(users.getPassword()==logInDto.getPassword()){
                return true;
            }
            return true;
        }
        return false;
    }
    
    public Users getUser(String email){
        Users users=userRepository.findByUserEmailIgnoreCase(email);
        return users;
    }

}
