package com.Kamran.gharKaBawarchi.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Long cookId;
    private Double totalAmount;
    private String customerName;
    private int numberOfPeople;
    private List<Long> foodItemIds;
    
}
