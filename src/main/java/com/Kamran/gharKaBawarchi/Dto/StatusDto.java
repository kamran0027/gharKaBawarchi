package com.Kamran.gharKaBawarchi.Dto;

import com.Kamran.gharKaBawarchi.Entity.Enum.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {

    private Long bookingId;
    private BookingStatus status;
}
