package com.Kamran.gharKaBawarchi.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Entity.City;
import com.Kamran.gharKaBawarchi.Respository.CityRepository;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public void updateCity(String cityName){
        cityRepository.findByCityName(cityName);
    }
    public List<City> getAllCitys(){
        return cityRepository.findAll();
    }
    
}
