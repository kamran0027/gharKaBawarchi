package com.Kamran.gharKaBawarchi.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    Long cookId;
    String bookingDate;
    String bookingTime;
    String specialRequests;
    // Add other relevant fields

}
