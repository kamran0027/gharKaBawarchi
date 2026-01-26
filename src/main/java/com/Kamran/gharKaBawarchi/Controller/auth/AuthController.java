package com.Kamran.gharKaBawarchi.Controller.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Kamran.gharKaBawarchi.Dto.CookRegisstrationDto;
import com.Kamran.gharKaBawarchi.Dto.LogInDto;
import com.Kamran.gharKaBawarchi.Dto.RegistrationDto;
import com.Kamran.gharKaBawarchi.Entity.City;
import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Service.CityService;
import com.Kamran.gharKaBawarchi.Service.CookService;
import com.Kamran.gharKaBawarchi.Service.JwtService;
import com.Kamran.gharKaBawarchi.Service.MenuService;
import com.Kamran.gharKaBawarchi.Service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private CityService cityService;
    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private CookService cookService;


    
    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("loginDto", new LogInDto());
        return "login";
    }

    @PostMapping("/login")
    public String doUserLogin(LogInDto loginForm, HttpServletResponse response,RedirectAttributes redirectAttributes,Model model) {
        try {
            System.out.println(loginForm.getUserName());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginForm.getUserName(), loginForm.getPassword()));

            // get role from authentication authorities
            String role = authentication.getAuthorities().stream().findFirst()
                    .map(Object::toString).orElse("ROLE_USER");
            String roleShort = role.startsWith("ROLE_") ? role.substring(5) : role;

            String token = jwtService.generateToken(loginForm.getUserName(), roleShort);
            System.out.println("*********************************");

            System.out.println("Generated JWT Token: " + token);

            Cookie cookie = new Cookie("JWT", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge((int) (jwtService.getExpirationMillis() / 1000)); // seconds
            response.addCookie(cookie);

            return "redirect:/home";
        } catch (AuthenticationException ex) {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            model.addAttribute("loginDto", new LogInDto());
            return "redirect:/login";
        }
    }
    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("registrationDTO", new RegistrationDto());
        model.addAttribute("allCitys", cityService.getAllCitys());
        return "registration-form";
    }

    @PostMapping("/register")
    public String doRegisterUser(RegistrationDto user,RedirectAttributes redirectAttributes) {
        userService.saveRegistration(user);
        if(user!=null){
            redirectAttributes.addFlashAttribute("message", "Registration successful! Please log in.");
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("error", "Registration failed. Please try again.");
        return "redirect:/register";
    }

    // cook log in 

    @GetMapping("/cook/login")
    public String showCookLogin(Model model){
        model.addAttribute("loginDto",new LogInDto());
        return "cook_login";
    }

    @PostMapping("/cook/login")
    public String doCookLogin(LogInDto loginForm, HttpServletResponse response, Model model) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginForm.getUserName(), loginForm.getPassword())
            );

            String role = authentication.getAuthorities().stream().findFirst()
                    .map(Object::toString).orElse("ROLE_COOK");
            String roleShort = role.startsWith("ROLE_") ? role.substring(5) : role;

            String token = jwtService.generateToken(loginForm.getUserName(), roleShort);

            Cookie cookie = new Cookie("JWT", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge((int) (jwtService.getExpirationMillis() / 1000));
            response.addCookie(cookie);

            return "redirect:/cook/home";
        } catch (AuthenticationException ex) {
            model.addAttribute("error", "Invalid username or password");
            model.addAttribute("loginDto", new LogInDto());
            return "cook_login";
        }
    }

    @GetMapping("/cook/register")
    public String showCookRegistrationForm(Model model){
        model.addAttribute("cookDto",new CookRegisstrationDto());
        model.addAttribute("allCitys",cityService.getAllCitys());
        System.out.println("**************************");
        List<City> cities=cityService.getAllCitys();
        for(City c:cities){
            System.out.println(c.getCityName());
        }
        model.addAttribute("foodItems", menuService.getAllMenuItems());
        return "cook_registration";
    }

    @PostMapping("/cook/register")
    public String processCookRegistration(CookRegisstrationDto cookDto,RedirectAttributes redirectAttributes){
        System.out.println("**************************");
        Cook cook=cookService.saveCook(cookDto);
        if (cook!=null) {
            redirectAttributes.addFlashAttribute("success","Registration successful! Please log in.");
        return "redirect:/cook/login";
        }
        redirectAttributes.addFlashAttribute("error","registration not completed due to server error");
        return "redirect:/cook/register";
        
    }

    // Logout clears cookie
    @PostMapping("/logout")
    public String logout(HttpServletResponse response,RedirectAttributes redirectAttributes) {
        Cookie cookie = new Cookie("JWT", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        redirectAttributes.addFlashAttribute("message","log out successfull");
        return "redirect:/login";
    }

}
