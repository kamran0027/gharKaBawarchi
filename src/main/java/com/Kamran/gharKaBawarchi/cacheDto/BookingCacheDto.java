package com.Kamran.gharKaBawarchi.cacheDto;

import java.util.List;


import com.Kamran.gharKaBawarchi.Entity.Enum.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookingCacheDto{
    private Long id;
    private String customerName;
    private BookingStatus status;
    private Double totalAmount;
    private String paymentStatus;
    private int numberOfPeople;

    private CookDto cook;
    private TimeSlotDto timeSlot;
    private List<MenuDto> menuItems;
}
