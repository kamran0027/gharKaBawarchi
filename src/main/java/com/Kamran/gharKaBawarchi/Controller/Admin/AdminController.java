package com.Kamran.gharKaBawarchi.Controller.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Kamran.gharKaBawarchi.Entity.City;
import com.Kamran.gharKaBawarchi.Entity.Users;

import com.Kamran.gharKaBawarchi.Respository.CityRepository;
import com.Kamran.gharKaBawarchi.Service.BookingService;
import com.Kamran.gharKaBawarchi.Service.CityService;
import com.Kamran.gharKaBawarchi.Service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CityService cityService;

    @Autowired
    private CityRepository cityRepository;
    
    
    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;

    @GetMapping()
    public String adminDashboard() {
        return "admin_dashboard";
    }

    @GetMapping("/allCitys")
    public String addCity(Model model) {
        List<City> citys = cityService.getAllCitys();
        model.addAttribute("citys", citys);
        return "all_city";
    }

    @GetMapping("/add-city")
    public String addCity() {
        return "add_city_form";
    }

    @PostMapping("/add-city")
    public String saveCity(@RequestParam("cityName") String cityName) {
        City city = new City();
        city.setCityName(cityName);
        cityRepository.save(city);
        return "redirect:/admin/allCitys";
    }

    @GetMapping("/view-bookings")
    public String viewOrders(Model model){
        model.addAttribute("bookings",bookingService.getAllBookings());
        return "admin_view_orders";
    }
    @GetMapping("/view-bookings/search")
    public String searchBooking(@RequestParam(required=false) String keyword, Model model){
        if (keyword==null || keyword.trim().isEmpty()) {
            return "redirect:/admin/view-bookings";
        }
        else{
            System.out.println("**************");
            System.out.println(bookingService.getBookingById(keyword).getMenuItems().getFirst().getMenuName());
            model.addAttribute("bookings",List.of(bookingService.getBookingById(keyword)));
            return "admin_view_orders";
        }
    }
    @GetMapping("/users")
    public String viewUser(Model model,@RequestParam(defaultValue = "0") int page){
        Page<Users> users = userService.getUserByPaging(page);
        model.addAttribute("users", users.getContent());
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("page", page);
        return "admin_view_users";
    }


    @GetMapping("/order/user/{id}")
    public String viewUserOrder(@PathVariable("id") Long userId, Model model) {
        model.addAttribute("bookings", userService.getAllOrderByUserId(userId));
        return "admin_view_orders";
    }

}
