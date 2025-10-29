package com.Kamran.gharKaBawarchi.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Kamran.gharKaBawarchi.Dto.AddressDto;
import com.Kamran.gharKaBawarchi.Dto.UserProfileDto;
import com.Kamran.gharKaBawarchi.Entity.Users;
import com.Kamran.gharKaBawarchi.Service.AddresService;
import com.Kamran.gharKaBawarchi.Service.UserService;



@Controller
@RequestMapping("/home")
public class UserProfileController {

    @Autowired
    private  UserService userService;

    @Autowired
    private AddresService addresService;

    @GetMapping("/profile")
    public String userProfile(Model model){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String userEmail=auth.getName();
        Users user= userService.getUser(userEmail);
        UserProfileDto userProfileDto=new UserProfileDto();
        userProfileDto.setFullName(user.getFullName());
        userProfileDto.setEmail(user.getUserEmail());
        userProfileDto.setPhoneNumber(user.getPhoneNumber());
        userProfileDto.setPassword(user.getPassword());
        userProfileDto.setPhoneNumber(user.getPhoneNumber());
        model.addAttribute("user", userProfileDto);

        AddressDto addressDto=addresService.getAddressByUserId(userEmail);
        model.addAttribute("address", addressDto);

        return "user_profile1";
    }

    @PostMapping("/profile/update")
    public String userProfileUpdate(@ModelAttribute UserProfileDto user, RedirectAttributes redirectAttributes){
        if (userService.updateProfile(user)) {
            redirectAttributes.addFlashAttribute("message","profile Updated Successfully !");
            return "redirect:/home/profile";
        }
        redirectAttributes.addFlashAttribute("error","profile-updation fail");
        return "redirect:/home/profile";
    }

    @PostMapping("/profile/update-address")
    public String userAddressUpdate(@ModelAttribute("address") AddressDto addressDto,RedirectAttributes redirectAttributes){
        //Later i fetch the user name using the JWT authentication token
        // and i can also use the security context holder to fetch the curent login  user details
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String userEmail=auth.getName();

        if(addresService.updateAddress(addressDto,userEmail)){
            redirectAttributes.addFlashAttribute("message","Address update succedfully");
            return "redirect:/home/profile";
        }
        redirectAttributes.addFlashAttribute("error","Address update fail");
        return "redirect:/home/profile";
    }
}
