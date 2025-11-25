package com.Kamran.gharKaBawarchi.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class CookProfileDto {
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private Double rating;
    private int experience;
    private String specialization;

}
