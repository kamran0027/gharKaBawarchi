package com.Kamran.gharKaBawarchi.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Kamran.gharKaBawarchi.Dto.AddressDto;
import com.Kamran.gharKaBawarchi.Entity.Address;
import com.Kamran.gharKaBawarchi.Respository.AddressRepository;

@Service
public class AddresService {

    @Autowired
    private AddressRepository addresRepository;
    @Autowired
    private UserService userService;

    public void updateAddress(Address addres){

    }

    public AddressDto getAddressByUserId(String userName){
        Optional<Address> address =addresRepository.findByUser(userService.getUser(userName));
        AddressDto addressDto=new AddressDto();
        if(address.isPresent()){
            addressDto.setStreet(address.get().getStreet());
            addressDto.setCity(address.get().getCity());
            addressDto.setState(address.get().getState());
            addressDto.setZipCode(address.get().getZipCode());
            addressDto.setCountry(address.get().getCountry());
            return addressDto;
        }
        return null;
    }

}
