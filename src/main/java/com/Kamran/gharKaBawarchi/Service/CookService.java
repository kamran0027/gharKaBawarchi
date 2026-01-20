package com.Kamran.gharKaBawarchi.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private SlotGeneratorService slotGeneratorService;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    private static final long MAX_BYTES=5*1024*1024; //5MB

    @Autowired
    private FileStorageService fileStorageService;

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

    public Cook getCookByUSerEmail(String userEmail){
        return cookRepository.findByCookEmail(userEmail).orElse(null);
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

        // uploading image 

        try {
            fileStorageService.validateImage(cookRegisstrationDto.getImage(), MAX_BYTES);
            String path=fileStorageService.storeFile(cookRegisstrationDto.getImage());
            cook.setImagePath(path);
            System.out.println("image uploaded succfully ");
        } catch (Exception e) {
            System.out.println("Registration fali in image Uploading : "+ e.getMessage());
            return null;
        }

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

        Cook saveCook=cookRepository.save(cook);
        slotGeneratorService.generateSlotsForCook(saveCook);

        return saveCook;

        
    }

    public Page<Cook> getCookByPage(int page){
        return cookRepository.findAll(PageRequest.of(page,5));

    }

}
