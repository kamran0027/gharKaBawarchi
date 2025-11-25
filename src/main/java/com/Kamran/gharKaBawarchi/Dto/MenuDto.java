package com.Kamran.gharKaBawarchi.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MenuDto {
    private Long menuId;
    private String menuItemName;
    private double price;

}
