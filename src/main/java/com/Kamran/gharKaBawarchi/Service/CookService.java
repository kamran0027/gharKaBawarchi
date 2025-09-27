package com.Kamran.gharKaBawarchi.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Entity.City;
import com.Kamran.gharKaBawarchi.Entity.Cook;
import com.Kamran.gharKaBawarchi.Respository.CookRepository;

@Service
public class CookService {

    @Autowired
    private final CookRepository cookRepository;

    public  CookService(CookRepository cookRepository){
        this.cookRepository=cookRepository;
    }
    public List<Cook> getAllCookByCity(City city){
        //City city=cityRepository.findByCityName(cityName);
        List<Cook> cooks=new ArrayList<>();
        cooks=cookRepository.findByCity(city);
        return cooks;
    }

}
