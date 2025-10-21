package com.Kamran.gharKaBawarchi.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Kamran.gharKaBawarchi.Dto.AddressDto;
import com.Kamran.gharKaBawarchi.Entity.Address;
import com.Kamran.gharKaBawarchi.Entity.Users;
import com.Kamran.gharKaBawarchi.Respository.AddressRepository;

@Service
public class AddresService {

    @Autowired
    private AddressRepository addresRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public boolean updateAddress(AddressDto addressDto, String userEmail){
        Users user=userService.getUser(userEmail);
        if(user!=null){
            Address address=user.getAddress();
            address.setCity(addressDto.getCity());
            address.setStreet(addressDto.getStreet());
            address.setState(addressDto.getState());
            address.setZipCode(addressDto.getZipCode());
            address.setCountry(addressDto.getCountry());

            addresRepository.save(address);
            return true;
        }
        return false;
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
