package com.Kamran.gharKaBawarchi.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Kamran.gharKaBawarchi.Dto.CookRegisstrationDto;
import com.Kamran.gharKaBawarchi.Entity.Booking;
import com.Kamran.gharKaBawarchi.Entity.City;
import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Entity.Menu;
import com.Kamran.gharKaBawarchi.Entity.Enum.Roles;
import com.Kamran.gharKaBawarchi.Respository.BookingRepository;
import com.Kamran.gharKaBawarchi.Respository.CityRepository;
import com.Kamran.gharKaBawarchi.Respository.CookRepository;
import com.Kamran.gharKaBawarchi.Respository.MenuRepository;

@Service
public class CookService {

    @Autowired
    private final MenuRepository menuRepository;

    @Autowired
    private final CookRepository cookRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    public  CookService(CookRepository cookRepository,MenuRepository menuRepository){
        this.cookRepository=cookRepository;
        this.menuRepository = menuRepository;
    }
    public List<Cook> getAllCookByCity(City city){
        //City city=cityRepository.findByCityName(cityName);
        List<Cook> cooks=new ArrayList<>();
        cooks=cookRepository.findByCity(city);
        return cooks;
    }
    public Optional<Cook> getCookById(Long id){
        return cookRepository.findById(id);
    } 
    
    public List<Booking> getAllBookingByCook(Cook cook){
        return bookingRepository.findByCook(cook);
    }

    @Transactional
    public Cook saveCook(CookRegisstrationDto cookRegisstrationDto){
        Cook cook=new Cook();
        cook.setRole(Roles.COOK);
        cook.setCookName(cookRegisstrationDto.getCookName());
        cook.setCookEmail(cookRegisstrationDto.getCookEmail());
        cook.setCookPassword(passwordEncoder.encode(cookRegisstrationDto.getCookPassword()));
        cook.setContactInfo(cookRegisstrationDto.getCookPhone());
        cook.setExperienceYears(cookRegisstrationDto.getExperienceYears());
        cook.setSpecialization(cookRegisstrationDto.getSpecialization());
        //filling city
        cook.setCity(cityRepository.findById(cookRegisstrationDto.getCityId()).get());

        //filling menu item to cook
        List<Menu> menuItem=menuRepository.findAllById(cookRegisstrationDto.getMenuId());
        List<Menu> cookMenu=new ArrayList<>();
        for(int i=0;i<menuItem.size();i++){
            cookMenu.add(new Menu());
        }
        for(int i=0;i<menuItem.size();i++){
            cookMenu.get(i).setCook(cook);
            cookMenu.get(i).setMenuName(menuItem.get(i).getMenuName());
            cookMenu.get(i).setPrice(menuItem.get(i).getPrice());
        }
        menuRepository.saveAll(cookMenu);

        return cookRepository.save(cook);
    }

}
