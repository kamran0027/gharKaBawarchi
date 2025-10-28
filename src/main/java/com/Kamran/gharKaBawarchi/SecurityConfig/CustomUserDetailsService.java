package com.Kamran.gharKaBawarchi.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Entity.Users;
import com.Kamran.gharKaBawarchi.Respository.CookRepository;
import com.Kamran.gharKaBawarchi.Respository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CookRepository cookRepository;

    
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        Users user=userRepository.findByUserEmailIgnoreCase(userName);
        if(user!=null){
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUserEmail())
                    .password(user.getPassword())

                    // get role in string 
                    .roles(user.getRoleString())
                    .build();
        }
        //now implement cook found 

        Cook cook=cookRepository.findByCookEmail(userName).orElse(null);
        if(cook!=null){
            return org.springframework.security.core.userdetails.User
                    .withUsername(cook.getCookEmail())
                    .password(cook.getCookPassword())
                    .roles(cook.getRoleString())
                    .build();
        }

        throw new UsernameNotFoundException("User not found" + userName);
    }
    

}
