package com.Kamran.gharKaBawarchi.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Kamran.gharKaBawarchi.Dto.BookingDto;
import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Service.BookingService;
import com.Kamran.gharKaBawarchi.Service.CookService;
import com.Kamran.gharKaBawarchi.Service.MenuService;

@Controller
@RequestMapping("/home/book")
public class BookingController {

    @Autowired
    private CookService cookService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/{id}")
    public String showBookingForm(@PathVariable("id") Long cookId, Model model) {
        Cook cook= cookService.getCookById(cookId).orElse(null);
        BookingDto bookingDto=new BookingDto();
        bookingDto.setCookId(cookId);
        model.addAttribute("cook", cook);
        model.addAttribute("foodItems",menuService.getMenuByCook(cook));
        model.addAttribute("bookingDto", bookingDto);
        return "booking-form";
    }

    @PostMapping("/submit")
    public String bookingCook(@ModelAttribute BookingDto bookingDto,Model model,RedirectAttributes redirectAttributes){
        Boolean isBooked=false;
        isBooked=bookingService.creatBooking(bookingDto);
        Cook cook=cookService.getCookById(bookingDto.getCookId()).orElse(null);

        if (isBooked) {
            model.addAttribute("booking",bookingDto);
            model.addAttribute("cook", cook);
            return "booking-success";
        } else {
            model.addAttribute("errorMessage", "Booking failed. Please try again.");
            return "booking-form";
        }

    }


}
