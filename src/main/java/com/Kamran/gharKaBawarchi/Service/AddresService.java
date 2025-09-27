package com.Kamran.gharKaBawarchi.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Entity.Addres;
import com.Kamran.gharKaBawarchi.Respository.AddresRepository;

@Service
public class AddresService {

    @Autowired
    private AddresRepository addresRepository;

    public void updateAddress(Addres addres){
    }

}
