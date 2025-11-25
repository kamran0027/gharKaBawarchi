package com.Kamran.gharKaBawarchi.Controller.Cook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Kamran.gharKaBawarchi.Dto.CookProfileDto;
import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Service.CookService;

@Controller
@RequestMapping("/cook")
public class profileController {
    
    
    @Autowired
    private CookService cookService;


    @GetMapping("/profile")
    public String showProfile(Model model){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();

        Cook cooks=cookService.getCookByUSerEmail(userName);
        if (cooks==null) {
            return "redirect:/cook/home";
        }
        CookProfileDto cookProfileDto=new CookProfileDto(cooks.getCookName(),cooks.getCookEmail()
                                                        ,cooks.getCookPassword(),cooks.getContactInfo()
                                                        ,cooks.getRating(),cooks.getExperienceYears()
                                                        ,cooks.getSpecialization());
        model.addAttribute("cook", cookProfileDto);
        return "cook_profile";
    }

    @GetMapping("/profile/edit-profile{}")
    public String showProfileUpdateForm(@PathVariable("id") Long cookId){
        return "";
    }
}
