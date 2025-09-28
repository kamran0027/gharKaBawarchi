package com.Kamran.gharKaBawarchi.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    private String details;
    private String date;
    private String time;
    private Double totalAmount;
    private String customerName;
    private String customerContact;
    private String address;
    private String specialInstructions;
    private List<String> menuItems;

    @ManyToOne
    @JoinColumn(name = "cook_id")
    private Cook cook;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;



}
