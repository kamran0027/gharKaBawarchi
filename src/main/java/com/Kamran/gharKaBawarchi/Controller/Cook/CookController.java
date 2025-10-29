package com.Kamran.gharKaBawarchi.Controller.Cook;

import java.util.Optional;

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

import com.Kamran.gharKaBawarchi.Dto.StatusDto;
import com.Kamran.gharKaBawarchi.Entity.Booking;
import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Entity.Enum.BookingStatus;
import com.Kamran.gharKaBawarchi.Respository.BookingRepository;
import com.Kamran.gharKaBawarchi.Respository.CookRepository;
import com.Kamran.gharKaBawarchi.Respository.TimeSlotRepository;
import com.Kamran.gharKaBawarchi.Service.CityService;
import com.Kamran.gharKaBawarchi.Service.CookService;
import com.Kamran.gharKaBawarchi.Service.MenuService;


@Controller
@RequestMapping("/cook")
public class CookController {

    @Autowired
    private CityService cityService;
    @Autowired
    private CookService cookService;
    @Autowired
    private CookRepository cookRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MenuService menuService;

    @Autowired
    private TimeSlotRepository slotRepository;

    // @GetMapping("/login")
    // public String showCookLogin(Model model){
    //     model.addAttribute("loginDto",new LogInDto());
    //     return "cook_login";
    // }

    @GetMapping("/home")
    public String getlogin(Model model,Authentication authentication,RedirectAttributes redirectAttributes){

        //Later i fetch the user name using the JWT authentication token
        // and i can also use the security context holder to fetch the curent login  user details
        authentication=SecurityContextHolder.getContext().getAuthentication();
        String userEmail=authentication.getName();
        Optional<Cook> cook=cookRepository.findByCookEmail(userEmail);
        if (cook.isPresent()) {
            model.addAttribute("cook", cook.get());
            model.addAttribute("orders",cookService.getAllBookingByCook(cook.get()));
            return "cook_dashboard";
        }
        redirectAttributes.addFlashAttribute("error1","servor time out plead login again");
        return "redirect:/cook/login";
        
    }

    @GetMapping("/home/update-status/{id}")
    public String updateBookingStatus(@PathVariable Long id,Model model){
        Booking booking=bookingRepository.findById(id).orElse(null);
        StatusDto statusDto=new StatusDto();
        if (booking!=null) {
            statusDto.setBookingId(booking.getId());
            statusDto.setStatus(booking.getStatus());
            model.addAttribute("statusDto",statusDto);
            model.addAttribute("statuses",BookingStatus.values());
        }
        return "status_update";
    }

    @PostMapping("/home/update-status")
    public String processUpdateBookingStatus(StatusDto statusDto, RedirectAttributes redirectAttributes){
        Optional<Booking> booking=bookingRepository.findById(statusDto.getBookingId());
        if (booking.isPresent()) {
            Booking existingBooking=booking.get();
            existingBooking.setStatus(statusDto.getStatus());
            bookingRepository.save(existingBooking);
            redirectAttributes.addFlashAttribute("message","Booking status updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error","Status not updated pleas try again later");
        }
        return "redirect:/cook/home";
    }

    
}
