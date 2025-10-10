package com.Kamran.gharKaBawarchi.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
public class RegistrationDto {
    private String Email;
    private String fullName;
    private String password;
    private String phoneNumber;

    //City for service
    private Long cityId;
    private AddressDto addressDto;
}
