package com.Kamran.gharKaBawarchi.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Entity.FavouriteCook;
import com.Kamran.gharKaBawarchi.Entity.Users;
import com.Kamran.gharKaBawarchi.Respository.FavouriteCookRepository;

@Service
public class FavourateCookService {

    @Autowired
    private UserService userService;

    @Autowired
    private FavouriteCookRepository favouriteCookRepository;

    public boolean saveFavourate(Long cookId){
        try {
            

            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String userName=authentication.getName();
            Users user=userService.getUser(userName);

            System.out.println("##############################");
            System.out.println(favouriteCookRepository.findByUserAndCookId(user, cookId).isEmpty());
            if(!favouriteCookRepository.findByUserAndCookId(user, cookId).isEmpty()){
                return false;
            }

            FavouriteCook favouriteCook=new FavouriteCook();
            favouriteCook.setUser(user);
            favouriteCook.setCookId(cookId);

            favouriteCookRepository.save(favouriteCook);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());

            return false;
        }

    }
    public boolean removeFromFavourate(Long cookId){
        try {
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String userName=authentication.getName();
            Users user=userService.getUser(userName);
            FavouriteCook favouriteCook=favouriteCookRepository.findByUserAndCookId(user, cookId).get();
            if (favouriteCook!=null) {
                favouriteCookRepository.delete(favouriteCook);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
