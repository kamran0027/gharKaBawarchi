package com.Kamran.gharKaBawarchi.Service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Kamran.gharKaBawarchi.Entity.Cook;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class CookServiceTest{

    @Autowired
    private CookService cookService;
    
    @Test
    public void testFindByCity(){
        List<Cook> cooks=cookService.getAllCookByCity(City city);
        cooks.forEach(cook -> log.info("Cook: {}", cook));
        System.out.println("-------------------");

    } 

}
