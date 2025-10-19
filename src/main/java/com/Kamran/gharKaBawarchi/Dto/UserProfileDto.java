package com.Kamran.gharKaBawarchi.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserProfileDto {
    private String fullName;
    private String email;
    private String phoneNumber;

    private String password;
    
}
