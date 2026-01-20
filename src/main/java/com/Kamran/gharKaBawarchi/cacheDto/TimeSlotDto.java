package com.Kamran.gharKaBawarchi.cacheDto;

import java.time.LocalDate;
import java.time.LocalTime;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TimeSlotDto {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
