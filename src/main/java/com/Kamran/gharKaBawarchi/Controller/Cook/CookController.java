package com.Kamran.gharKaBawarchi.Controller.Cook;

import java.util.List;
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

import com.Kamran.gharKaBawarchi.Dto.MenuDto;
import com.Kamran.gharKaBawarchi.Dto.StatusDto;
import com.Kamran.gharKaBawarchi.Entity.Booking;
import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Entity.Menu;
import com.Kamran.gharKaBawarchi.Entity.Enum.BookingStatus;
import com.Kamran.gharKaBawarchi.Respository.BookingRepository;
import com.Kamran.gharKaBawarchi.Respository.CookRepository;
import com.Kamran.gharKaBawarchi.Respository.MenuRepository;
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
    private MenuRepository menuRepository;

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

    @GetMapping("/menuItem")
    public String ShowMenuItem(Model model){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        Cook cook=cookRepository.findByCookEmail(userName).get();
        model.addAttribute("cookName",cook.getCookName());
        List<Menu> menus=menuService.getMenuByCook(cook.getCookId());
        model.addAttribute("menus",menus);

        return "cook_menuItem";
    }
    @GetMapping("/menuItem/add-menu")
    public String addMenuItem(Model model){
        MenuDto menuDto=new MenuDto();
        menuDto.setMenuId(null);
        model.addAttribute("menuDto",menuDto);

        return "menuItem-Add-form";
    }

    @PostMapping("/menuItem/save")
    public String saveMenuItem(MenuDto menuDto, RedirectAttributes redirectAttributes){
            System.out.println("************************");
            System.out.println(menuDto.getMenuItemName());
            System.out.println(menuDto.getPrice());
        if (menuService.saveMenu(menuDto)) {
            redirectAttributes.addFlashAttribute("message","menu add succesfully");
            return "redirect:/cook/menuItem";
        }
        redirectAttributes.addFlashAttribute("message","failed to add menu");
        return "redirect:/cook/menuItem";
    }

    //update the Menu item from cook 
    @GetMapping("/menuItem/edit/{id}")
    public String editMenuItem(@PathVariable("id") Long menuId,Model model,RedirectAttributes redirectAttributes){
        Menu menu=menuRepository.findById(menuId).orElse(null);
        System.out.println("******************************");
        System.out.println(menu.getMenuName());
        MenuDto menuDto=new MenuDto(menu.getMenuId(),menu.getMenuName(),menu.getPrice());
    
        if(menu!=null){
            model.addAttribute("menuDto",menuDto);
            return "menuItem-Add-form";
        }
        redirectAttributes.addFlashAttribute("message","failed to update");
        return"redirect:/cook/menuItem";
    }

    // Delete Menu item 

    @GetMapping("/menuItem/{id}")
    public String deleteMenu(@PathVariable("id") Long menuId,RedirectAttributes redirectAttributes){
        if (menuService.deleteMenu(menuId)) {
            redirectAttributes.addFlashAttribute("message","menu Item delete Succfelly");
            return "redirect:/cook/menuItem";
        }
        redirectAttributes.addFlashAttribute("message","failed to delete MenuItem");
        return "redirect:/cook/menuItem";
    }

}
