package com.Kamran.gharKaBawarchi.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CookRegisstrationDto {
    private String cookName;
    private String cookEmail;
    private String specialization;
    private int experienceYears;
    private String cookPassword;
    private String cookPhone;
    private Long cityId;
    

    private List<Long> menuId;

}
