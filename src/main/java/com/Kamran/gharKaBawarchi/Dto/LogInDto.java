package com.Kamran.gharKaBawarchi.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class LogInDto {
    private String userName;
    private String password;
}
