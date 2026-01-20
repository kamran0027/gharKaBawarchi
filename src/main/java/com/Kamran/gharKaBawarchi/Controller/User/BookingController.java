package com.Kamran.gharKaBawarchi.Controller.User;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Kamran.gharKaBawarchi.Dto.BookingDto;
import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Entity.TimeSlot;
import com.Kamran.gharKaBawarchi.Respository.TimeSlotRepository;
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

    @Autowired
    private TimeSlotRepository slotRepository;

    @GetMapping("/form")
    public String showBookingForm(@RequestParam Long cookId, @RequestParam long slotId, Model model) {
        Cook cook= cookService.getCookById(cookId).orElse(null);
        TimeSlot slot=slotRepository.findById(slotId).orElse(null);
        BookingDto bookingDto=new BookingDto();
        bookingDto.setCookId(cookId);
        bookingDto.setSlotId(slotId);
        model.addAttribute("cookName", cook.getCookName());
        model.addAttribute("foodItems",menuService.getMenuByCook(cookId));
        model.addAttribute("slot",slot);
        model.addAttribute("bookingDto", bookingDto);
        return "booking-form";
    }

    @PostMapping("/submit")
    public String bookingCook(@ModelAttribute BookingDto bookingDto,Model model,RedirectAttributes redirectAttributes) throws Exception{
        Boolean isBooked=false;
        isBooked=bookingService.creatBooking(bookingDto);
        Cook cook=cookService.getCookById(bookingDto.getCookId()).orElse(null);
        System.out.println("***********************");
        System.out.println(bookingDto.getPaymentMode());
        System.out.println("***************");

        if (isBooked) {
            model.addAttribute("booking",bookingDto);
            model.addAttribute("cook", cook);
            return "booking-success";
        } else {
            redirectAttributes.addFlashAttribute("error", "Booking failed. Please try again.");
            return "redirect:/home";
        }

    }
    @GetMapping("/calendar")
    public String viewCookCalendar(@RequestParam Long cookId,
                                    @RequestParam(required = false) Long selectedSlotId,
                                    Model model) {

        Cook cook = cookService.getCookById(cookId).orElse(null);
        LocalDate today = LocalDate.now();
        LocalDate monthEnd = today.plusMonths(1);

        System.out.println("**************************************************");
        List<TimeSlot> slots =slotRepository.findByCookAndDateBetweenOrderByDateAscStartTimeAsc(cook, today, monthEnd);

        System.out.println("**************************************************");
        Map<LocalDate, List<TimeSlot>> slotsByDate = slots.stream()
                .collect(Collectors.groupingBy(TimeSlot::getDate, LinkedHashMap::new, Collectors.toList()));

        model.addAttribute("cook", cook);
        model.addAttribute("slotsByDate", slotsByDate);
        model.addAttribute("selectedSlotId", selectedSlotId);

        return "cook_calender";
    }

    @GetMapping("/cancel")
    public String cancleBooking(@RequestParam Long bookingId, RedirectAttributes redirectAttributes){
        System.out.println("***************************************");
        if (bookingService.cancleBooking(bookingId)) {
            redirectAttributes.addFlashAttribute("message","Booking cancle");
            return "redirect:/home/order";
        }
        redirectAttributes.addFlashAttribute("message","cancelation failed");
        return "redirect:/home/order";
        
    }


}
