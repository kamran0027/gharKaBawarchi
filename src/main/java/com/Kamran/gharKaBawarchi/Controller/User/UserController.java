package com.Kamran.gharKaBawarchi.Controller.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Kamran.gharKaBawarchi.Dto.CityDto;
import com.Kamran.gharKaBawarchi.Dto.LogInDto;
import com.Kamran.gharKaBawarchi.Dto.RegistrationDto;
import com.Kamran.gharKaBawarchi.Entity.Booking;
import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Entity.Users;
import com.Kamran.gharKaBawarchi.Service.CityService;
import com.Kamran.gharKaBawarchi.Service.CookService;
import com.Kamran.gharKaBawarchi.Service.UserService;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final CookService cookService;

    @Autowired
    private CityService cityService;
    public UserController(UserService userService, CookService cookService) {
        this.userService = userService;
        this.cookService=cookService;
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("loginDto", new LogInDto());
        return "login";
    }

    @PostMapping("/home")
    public String procesLogin(LogInDto logInDto, Model model, RedirectAttributes redirectAttributes){
        System.out.println("********************************");
        System.out.println(logInDto.getUserName());
        System.out.println(logInDto.getPassword());
        Boolean isValid=userService.processLogin(logInDto);
        System.out.println("*****************************");
        System.out.println(isValid);
        if(isValid){
            Users user=userService.getUser(logInDto.getUserName());
            System.out.println(logInDto.getUserName());
            model.addAttribute("user", user);
            model.addAttribute("cityDto",new CityDto());
            model.addAttribute("allCitys", cityService.getAllCitys());
            List<Cook> cooks=cookService.getAllCookByCity(user.getCity());
            model.addAttribute("cooks", cooks);

            return "user_dashboard";
        }
        return "redirect:/login?error=true";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("registrationDTO", new RegistrationDto());
        model.addAttribute("allCitys", cityService.getAllCitys());
        return "registration-form";
    }

    @PostMapping("/register")
    public String processRegistration(RegistrationDto registrationDto, Model model,RedirectAttributes redirectAttributes){
        Users user=userService.saveRegistration(registrationDto);
        if(user!=null){
            redirectAttributes.addFlashAttribute("message", "Registration successful! Please log in.");
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("error", "Registration failed. Please try again.");
        return "redirect:/register";
    }

    @GetMapping("/home/order")
    public String viewOrder(Model model){
        List<Booking> booking=userService.getAllOrderByUserId(1L);
        model.addAttribute("bookings", booking);
        return "user_order";
    }

    // @GetMapping("/home/book/{cookId}")
    // public String bookCook(@PathVariable Long cookId, Model model){
    //     Optional<Cook> cook=cookService.getCookById(cookId);
    //     model.addAttribute("cook", cook.get());
    //     model.addAttribute("bookingDto", new BookingDto());
    //     return "booking_form";
        
    // }

    // @PostMapping("/home/book/confirm")
    // public String confirmBooking(BookingDto bookingDto,Model model, RedirectAttributes redirectAttributes){

    //     Boolean isBooked=userService.createBooking(bookingDto, bookingDto.getCookId());
    //     if(isBooked){
    //         redirectAttributes.addFlashAttribute("message", "Booking successful!");
    //         return "redirect:/home";
    //     }else{
    //         redirectAttributes.addFlashAttribute("error", "Booking failed. Please try again.");
    //         return "redirect:/home/book/"+bookingDto.getCookId();
    //     }
    // }

}
