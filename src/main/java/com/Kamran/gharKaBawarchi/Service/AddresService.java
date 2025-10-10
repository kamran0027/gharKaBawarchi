package com.Kamran.gharKaBawarchi.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Entity.Address;
import com.Kamran.gharKaBawarchi.Respository.AddressRepository;

@Service
public class AddresService {

    @Autowired
    private AddressRepository addresRepository;

    public void updateAddress(Address addres){
    }

}
