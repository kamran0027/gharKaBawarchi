package com.Kamran.gharKaBawarchi.Controller.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Kamran.gharKaBawarchi.Dto.CityDto;
import com.Kamran.gharKaBawarchi.Entity.Booking;
import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Entity.Users;
import com.Kamran.gharKaBawarchi.Respository.CookRepository;
import com.Kamran.gharKaBawarchi.Respository.FavouriteCookRepository;
import com.Kamran.gharKaBawarchi.Service.CityService;
import com.Kamran.gharKaBawarchi.Service.CookService;
import com.Kamran.gharKaBawarchi.Service.FavourateCookService;
import com.Kamran.gharKaBawarchi.Service.UserService;


@Controller
@RequestMapping
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final CookService cookService;

    @Autowired
    private FavourateCookService favourateCookService;

    @Autowired
    private FavouriteCookRepository favouriteCookRepository;

    @Autowired
    private CookRepository cookRepository;

    @Autowired
    private CityService cityService;
    public UserController(UserService userService, CookService cookService) {
        this.userService = userService;
        this.cookService=cookService;
    }

    // @GetMapping("/login")
    // public String loginPage(Model model){
    //     model.addAttribute("loginDto", new LogInDto());
    //     return "login";
    // }
    @GetMapping("/home")
    public String getMethodName(Model model){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String userEmail=auth.getName();
        Users user=userService.getUser(userEmail);
        model.addAttribute("user", user);
        model.addAttribute("cityDto",new CityDto());
        model.addAttribute("allCitys", cityService.getAllCitys());
        List<Cook> cooks=cookService.getAllCookByCity(user.getCity());
        model.addAttribute("cooks", cooks);
        return"user_dashboard";
    }
    
    @GetMapping("/home/order")
    public String viewOrder(Model model){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String userEmail=auth.getName();
        List<Booking> booking=userService.getAllOrderByUserName(userEmail);
        model.addAttribute("bookings", booking);
        return "user_order";
    }
    @PostMapping("/home/change-city")
    public String changeCityProcess(CityDto cityDto, RedirectAttributes redirectAttributes){
        boolean isUpdate=cityService.updateCity(cityDto.getCityId());
        if (isUpdate) {
            redirectAttributes.addFlashAttribute("message", "City updated successfully.");
            return "redirect:/home";
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update city. Please try again.");
            return "redirect:/home";
        }
    }


    @GetMapping("/home/favourate")

    public String showFavourate(Model model){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        Users user=userService.getUser(userName);
        List<Long> cookId=favouriteCookRepository.findCookIdsByUser(user);
        List<Cook> cooks=cookRepository.findAllById(cookId);
        model.addAttribute("cooks", cooks);

        return "favourate-cook";
    }

    @GetMapping("/home/favourate/{id}")
    public String addToFavourate(@PathVariable("id") Long cookId,RedirectAttributes redirectAttributes){
        if(favourateCookService.saveFavourate(cookId)){

            return "redirect:/home/favourate";
        }
        redirectAttributes.addFlashAttribute("message","faled to Add cook to favourate list Or it is Alredy in Favourate List");
        return "redirect:/home/favourate";
    }

}
