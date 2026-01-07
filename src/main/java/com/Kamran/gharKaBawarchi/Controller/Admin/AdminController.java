package com.Kamran.gharKaBawarchi.Controller.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Kamran.gharKaBawarchi.Entity.City;
import com.Kamran.gharKaBawarchi.Respository.BookingRepository;
import com.Kamran.gharKaBawarchi.Respository.CityRepository;
import com.Kamran.gharKaBawarchi.Service.CityService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CityService cityService;

    @Autowired
    private CityRepository cityRepository;
    
    @Autowired
    private BookingRepository bookingRepository;

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
        model.addAttribute("bookings",bookingRepository.findAll());
        return "admin_view_orders";
    }
    @GetMapping("/view-bookings/search")
    public String searchBooking(@RequestParam(required=false) String keyword, Model model){
        if (keyword==null || keyword.trim().isEmpty()) {
            return "redirect:/admin/view-bookings";
        }
        else{
            model.addAttribute("bookings",bookingRepository.findById(Long.parseLong(keyword)).orElse(null));
            return "admin_view_orders";
        }
    }

}
