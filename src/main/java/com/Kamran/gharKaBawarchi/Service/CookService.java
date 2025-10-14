package com.Kamran.gharKaBawarchi.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Dto.CookRegisstrationDto;
import com.Kamran.gharKaBawarchi.Dto.LogInDto;
import com.Kamran.gharKaBawarchi.Entity.Booking;
import com.Kamran.gharKaBawarchi.Entity.City;
import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Entity.Menu;
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

    public Optional<Cook> cookLoginProcess(LogInDto logInDto){
        Optional<Cook> cook=cookRepository.findByCookEmail(logInDto.getUserName());
        if(cook.isPresent()){
            if (cook.get().getCookPassword().equals(logInDto.getPassword())) {
                return cook;
            }
        }
        return null;
    }

    public List<Booking> getAllBookingByCook(Cook cook){
        return bookingRepository.findByCook(cook);
    }

    public Cook saveCook(CookRegisstrationDto cookRegisstrationDto){
        Cook cook=new Cook();
        cook.setCookName(cookRegisstrationDto.getCookName());
        cook.setCookEmail(cookRegisstrationDto.getCookEmail());
        cook.setCookPassword(cookRegisstrationDto.getCookPassword());
        cook.setContactInfo(cookRegisstrationDto.getCookPhone());
        cook.setExperienceYears(cookRegisstrationDto.getExperienceYears());
        cook.setSpecialization(cookRegisstrationDto.getSpecialization());
        //filling city
        cook.setCity(cityRepository.findById(cookRegisstrationDto.getCityId()).get());

        //filling menu item to cook
        List<Menu> menuItem=menuRepository.findAllById(cookRegisstrationDto.getMenuId());
        cook.setMenuItems(menuItem);

        return cookRepository.save(cook);
    }

}
