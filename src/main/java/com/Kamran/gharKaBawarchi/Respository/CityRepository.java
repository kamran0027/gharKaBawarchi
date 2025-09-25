package com.Kamran.gharKaBawarchi.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Kamran.gharKaBawarchi.Entity.City;

@Repository
public interface CityRepository extends JpaRepository<City,Long>{
    City findByCityName(String cityName);

}
