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
    private Long slotId;
    private Double totalAmount;
    private String customerName;
    private int numberOfPeople;
    private String paymentMode; // COD | ONLINE
    private String razorpayPaymentId;
    private String razorpayOrderId;
    private String razorpaySignature;
    private List<Long> foodItemIds;
    
}
