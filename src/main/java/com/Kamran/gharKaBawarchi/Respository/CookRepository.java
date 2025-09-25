package com.Kamran.gharKaBawarchi.Respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Kamran.gharKaBawarchi.Entity.City;
import com.Kamran.gharKaBawarchi.Entity.Cook;


@Repository
public interface CookRepository  extends JpaRepository<Cook,Long>{
    // List<Cook> findByCityName(String cityName);
    List<Cook> findByCity(City city);


}
